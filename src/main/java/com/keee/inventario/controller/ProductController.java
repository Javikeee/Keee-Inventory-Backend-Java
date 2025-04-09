package com.keee.inventario.controller;

import com.keee.inventario.controller.entity.ApiResponse;
import com.keee.inventario.dto.ProductDTO;
import com.keee.inventario.dto.ProductRequestDTO;
import com.keee.inventario.dto.ProductResponseDTO;
import com.keee.inventario.dto.SupplierResponseDTO;
import com.keee.inventario.helper.MessageHelper;
import com.keee.inventario.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final MessageHelper messageHelper;

    /**
     * Method to get all the products
     *
     * @param language
     * @return
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getAllProducts(
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {

        Locale locale = Locale.forLanguageTag(language);
        List<ProductResponseDTO> products = productService.getAllProducts();
        String message = messageHelper.getMessage("product.list", locale);
        return ResponseEntity.ok(new ApiResponse<>(message, products));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getAllActiveProducts(
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {

        Locale locale = Locale.forLanguageTag(language);
        List<ProductResponseDTO> products = productService.getAllActiveProducts();
        String message = messageHelper.getMessage("product.list", locale);
        return ResponseEntity.ok(new ApiResponse<>(message, products));
    }

    /**
     * Method to get a product
     *
     * @param id
     * @param language
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> getProductById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {

        Locale locale = Locale.forLanguageTag(language);
        ProductResponseDTO product = productService.getProductById(id, locale);
        String message = messageHelper.getMessage("product.found", locale);
        return ResponseEntity.ok(new ApiResponse<>(message, product));
    }

    /**
     * Method to get all products from a supplier
     *
     * @param id
     * @param language
     * @return
     */
    @GetMapping("supplier/{id}")
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getAllProductsFromASupplier(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {

        Locale locale = Locale.forLanguageTag(language);
        List<ProductResponseDTO> products = productService.getAllProductsBySupplierId(id, locale);
        String message = messageHelper.getMessage("product.list.supplier", locale);
        return ResponseEntity.ok(new ApiResponse<>(message, products));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponseDTO>> createProduct(
        @RequestHeader(value = "Accept.Language", defaultValue = "en" ) String language,
        @RequestBody @Valid ProductRequestDTO productRequestDTO
    ){
        Locale locale = Locale.forLanguageTag(language);
        ProductResponseDTO createdProduct = productService.createProduct(productRequestDTO);
        String message = messageHelper.getMessage("product.created", locale);
        return ResponseEntity.ok((new ApiResponse<>(message, createdProduct)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> updateProduct(
            @PathVariable Long id,
            @RequestBody @Valid @NotNull ProductDTO productDTO,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {

        Locale locale = Locale.forLanguageTag(language);
        ProductResponseDTO updatedProduct = productService.updateProductPartial(id, productDTO);
        String message = messageHelper.getMessage("product.updated", locale);
        return ResponseEntity.ok(new ApiResponse<>(message, updatedProduct));
    }

    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> deactivateProduct(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {

        Locale locale = Locale.forLanguageTag(language);
        ProductResponseDTO deactivatedProduct = productService.deactivateProduct(id);
        String message = messageHelper.getMessage("product.deactivated", locale);
        return ResponseEntity.ok(new ApiResponse<>(message, deactivatedProduct));
    }

}
