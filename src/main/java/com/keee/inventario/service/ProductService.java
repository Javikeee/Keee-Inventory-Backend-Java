package com.keee.inventario.service;

import com.keee.inventario.dto.ProductDTO;
import com.keee.inventario.dto.ProductRequestDTO;
import com.keee.inventario.dto.ProductResponseDTO;

import java.util.List;
import java.util.Locale;

public interface ProductService {

    List<ProductResponseDTO> getAllProducts();

    ProductResponseDTO getProductById(Long id, Locale locale);

    List<ProductResponseDTO> getAllActiveProducts();

    List<ProductResponseDTO> getAllProductsBySupplierId(Long id, Locale locale);

    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO);

    ProductResponseDTO updateProductPartial(Long id, ProductDTO productDTO);

    ProductResponseDTO deactivateProduct(Long id);
}
