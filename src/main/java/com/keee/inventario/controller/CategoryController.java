package com.keee.inventario.controller;

import com.keee.inventario.dto.CategoryDTO;
import com.keee.inventario.entity.Category;
import com.keee.inventario.helper.MessageHelper;
import com.keee.inventario.service.CategoryService;
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

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> createCategory(
            @RequestBody CategoryDTO categoryDTO,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {

        categoryService.createCategory(categoryDTO);
        Locale locale = Locale.forLanguageTag(language);
        String message = messageHelper.getMessage("category.created", locale);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(
            @PathVariable Long id,
            @RequestBody CategoryDTO categoryDTO,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {

        categoryService.updateCategory(id, categoryDTO);
        Locale locale = Locale.forLanguageTag(language);
        String message = messageHelper.getMessage("category.updated", locale);
        return ResponseEntity.ok(message);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> patchCategory(
            @PathVariable Long id,
            @RequestBody CategoryDTO categoryDTO,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {

        categoryService.patchCategory(id, categoryDTO);
        Locale locale = Locale.forLanguageTag(language);
        String message = messageHelper.getMessage("category.updated", locale);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {

        String resultMessage = categoryService.deleteCategory(id);
        Locale locale = Locale.forLanguageTag(language);
        String message = messageHelper.getMessage(resultMessage, locale);
        return ResponseEntity.ok(message);
    }
}
