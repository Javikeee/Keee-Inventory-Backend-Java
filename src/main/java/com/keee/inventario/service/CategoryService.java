package com.keee.inventario.service;

import com.keee.inventario.dto.CategoryDTO;
import com.keee.inventario.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> getAllCategories();
    Optional<Category> getCategoryById(Long id);
    Category createCategory(CategoryDTO categoryDTO);
    Category updateCategory(Long id, CategoryDTO categoryDTO);
    Category patchCategory(Long id, CategoryDTO categoryDTO);
    String deleteCategory(Long id);
}
