package com.keee.inventario.service.impl;

import com.keee.inventario.dto.ProductDTO;
import com.keee.inventario.dto.ProductRequestDTO;
import com.keee.inventario.dto.ProductResponseDTO;
import com.keee.inventario.entity.Category;
import com.keee.inventario.entity.Product;
import com.keee.inventario.entity.Supplier;
import com.keee.inventario.exception.ResourceNotFoundException;
import com.keee.inventario.helper.MessageHelper;
import com.keee.inventario.mapper.ProductMapper;
import com.keee.inventario.repository.CategoryRepository;
import com.keee.inventario.repository.ProductRepository;
import com.keee.inventario.repository.SupplierRepository;
import com.keee.inventario.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final CategoryRepository categoryRepository;
    private final MessageHelper messageHelper;
    private final ProductMapper productMapper;

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        try {
            return productRepository.findAll()
                    .stream()
                    .map(productMapper::entityToResponseDto)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new RuntimeException(messageHelper.getMessage("product.error.fetch", Locale.getDefault()), e);
        }
    }

    @Override
    public ProductResponseDTO getProductById(Long id, Locale locale) {
        return productRepository.findById(id)
                .map(productMapper::entityToResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageHelper.getMessage("product.error.not_found", locale) + " " + id
                ));
    }

    @Override
    public List<ProductResponseDTO> getAllActiveProducts() {
        try {
            return productRepository.findByActiveTrue()
                    .stream()
                    .map(productMapper::entityToResponseDto)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new RuntimeException(messageHelper.getMessage("product.error.fetch", Locale.getDefault()), e);
        }
    }

    @Override
    public List<ProductResponseDTO> getAllProductsBySupplierId(Long supplierId, Locale locale) {
        try {
            return productRepository.findAllBySupplierId(supplierId)
                    .stream()
                    .map(productMapper::entityToResponseDto)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new RuntimeException(messageHelper.getMessage("product.error.not_found_supplier", locale) + " " + supplierId, e);
        }
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        try {
            Category category = categoryRepository.findById(productRequestDTO.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            messageHelper.getMessage("category.error.not_found", Locale.getDefault())
                                    + " " + productRequestDTO.getCategoryId()));

            Supplier supplier = supplierRepository.findById(productRequestDTO.getSupplierId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            messageHelper.getMessage("supplier.error.not_found", Locale.getDefault())
                                    + " " + productRequestDTO.getSupplierId()));

            Product product = productMapper.requestDtoToEntity(productRequestDTO, category, supplier);
            Product savedProduct = productRepository.save(product);

            return productMapper.entityToResponseDto(savedProduct);
        } catch (DataAccessException e) {
            throw new RuntimeException(messageHelper.getMessage("product.error.create", Locale.getDefault()), e);
        }
    }

    @Override
    public ProductResponseDTO updateProductPartial(Long id, ProductDTO productDTO) {
        return productRepository.findById(id)
                .map(product -> {
                    if (Objects.nonNull(productDTO.getName())) {
                        product.setName(productDTO.getName());
                    }
                    if (Objects.nonNull(productDTO.getDescription())) {
                        product.setDescription(productDTO.getDescription());
                    }
                    if (Objects.nonNull(productDTO.getStockMinimum())) {
                        product.setStockMinimum(productDTO.getStockMinimum());
                    }
                    if (Objects.nonNull(productDTO.getPrice())) {
                        product.setPrice(productDTO.getPrice());
                    }
                    if (Objects.nonNull(productDTO.getCategoryId())) {
                        Category category = categoryRepository.findById(productDTO.getCategoryId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                        messageHelper.getMessage("category.error.not_found", Locale.getDefault())
                                                + " " + productDTO.getCategoryId()));
                        product.setCategory(category);
                    }
                    if (Objects.nonNull(productDTO.getSupplierId())) {
                        Supplier supplier = supplierRepository.findById(productDTO.getSupplierId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                        messageHelper.getMessage("supplier.error.not_found", Locale.getDefault())
                                                + " " + productDTO.getSupplierId()));
                        product.setSupplier(supplier);
                    }

                    Product updatedProduct = productRepository.save(product);
                    return productMapper.entityToResponseDto(updatedProduct);
                })
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageHelper.getMessage("product.error.not_found", Locale.getDefault()) + id));
    }

    @Override
    public ProductResponseDTO deactivateProduct(Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setActive(false);
                    product.setStockMinimum(0);
                    Product updated = productRepository.save(product);
                    return productMapper.entityToResponseDto(updated);
                })
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageHelper.getMessage("product.error.not_found", Locale.getDefault()) + id));
    }

}
