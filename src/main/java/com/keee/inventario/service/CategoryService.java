package com.keee.inventario.service;

import com.keee.inventario.dto.CategoryDTO;
import com.keee.inventario.dto.CategoryRequestDTO;
import com.keee.inventario.dto.CategoryResponseDTO;

import java.util.List;
import java.util.Locale;

public interface CategoryService {

    List<CategoryResponseDTO> getAllCategories();

    CategoryResponseDTO getCategoryById(Long id, Locale locale);

    CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO);

    CategoryResponseDTO updateCategoryPartial(Long id, CategoryDTO categoryRequestDTO);

    CategoryResponseDTO changeCategoryStatus(Long id, boolean isActive);

}
