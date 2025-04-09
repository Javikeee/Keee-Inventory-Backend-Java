package com.keee.inventario.mapper;

import com.keee.inventario.dto.ProductRequestDTO;
import com.keee.inventario.dto.ProductResponseDTO;
import com.keee.inventario.entity.Category;
import com.keee.inventario.entity.Product;
import com.keee.inventario.entity.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component // @Component, deberías anotarlos así para que puedan inyectarse automáticamente.
@RequiredArgsConstructor
public class ProductMapper {

    private final CategoryMapper categoryMapper;
    private final SupplierMapper supplierMapper;

    public ProductResponseDTO entityToResponseDto(Product product) {
        return ProductResponseDTO.builder()
                .name(product.getName())
                .description(product.getDescription())
                .stockNow(product.getStockNow())
                .stockMinimum(product.getStockMinimum())
                .price(product.getPrice())
                .category(categoryMapper.entityToDto(product.getCategory()))
                .supplier(supplierMapper.entityToDto(product.getSupplier()))
                .build();
    }

    public Product requestDtoToEntity(ProductRequestDTO dto, Category category, Supplier supplier) {
        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .stockMinimum(dto.getStockMinimum())
                .price(dto.getPrice())
                .category(category)
                .supplier(supplier)
                .build();
    }
}

