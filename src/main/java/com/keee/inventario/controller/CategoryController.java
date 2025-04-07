package com.keee.inventario.controller;

import com.keee.inventario.controller.entity.ApiResponse;
import com.keee.inventario.dto.CategoryDTO;
import com.keee.inventario.dto.CategoryRequestDTO;
import com.keee.inventario.dto.CategoryResponseDTO;
import com.keee.inventario.helper.MessageHelper;
import com.keee.inventario.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final MessageHelper messageHelper;

    /**
     * Method to get all the categories
     *
     * @param language
     * @return
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponseDTO>>> getAllCategories(@RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {
        Locale locale = Locale.forLanguageTag(language);
        List<CategoryResponseDTO> suppliers = categoryService.getAllCategories();
        String message = messageHelper.getMessage("category.list", locale);
        return ResponseEntity.ok(new ApiResponse<>(message, suppliers));
    }

    /**
     * Method to get a category
     *
     * @param id
     * @param language
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> getCategoryById(@PathVariable Long id, @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {
        Locale locale = Locale.forLanguageTag(language);
        CategoryResponseDTO categoryResponseDTO = categoryService.getCategoryById(id, locale);
        String message = messageHelper.getMessage("category.found", locale);
        return ResponseEntity.ok(new ApiResponse<>(message, categoryResponseDTO));
    }

    /**
     * Method to create a new category
     *
     * @param categoryRequestDTO
     * @param language
     * @return
     */
    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> createCategory(
            @RequestBody  @Valid CategoryRequestDTO categoryRequestDTO,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {

        Locale locale = Locale.forLanguageTag(language);
        CategoryResponseDTO createdSupplier = categoryService.createCategory(categoryRequestDTO);
        String message = messageHelper.getMessage("category.created", locale);
        return ResponseEntity.ok(new ApiResponse<>(message, createdSupplier));
    }

    /**
     * Method to change category status
     *
     * Una categoria nunca podrá ser borrada, solo dado de baja, ya que eliminar  supone
     * perder información de productos. Y no queremos perder esta informacion
     *
     * @param id
     * @param isActive
     * @param language
     * @return
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> changeCategoryStatus(
            @PathVariable Long id,
            @RequestParam boolean isActive,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {

        Locale locale = Locale.forLanguageTag(language);
        CategoryResponseDTO updatedCategory = categoryService.changeCategoryStatus(id, isActive);
        String messageKey = isActive ? "category.activated" : "category.deactivated";
        String message = messageHelper.getMessage(messageKey, locale);
        return ResponseEntity.ok(new ApiResponse<>(message, updatedCategory));
    }

    /**
     * Method to update category info
     *
     * @param id
     * @param categoryDTO
     * @param language
     * @return
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> updateCategory(
            @PathVariable Long id,
            @RequestBody @Valid CategoryDTO categoryDTO,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {

        Locale locale = Locale.forLanguageTag(language);
        CategoryResponseDTO updatedCategory = categoryService.updateCategoryPartial(id, categoryDTO);
        String message = messageHelper.getMessage("category.updated", locale);
        return ResponseEntity.ok(new ApiResponse<>(message, updatedCategory));
    }
}
