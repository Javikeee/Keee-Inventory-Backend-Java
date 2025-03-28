package com.keee.inventario.service.impl;

import com.keee.inventario.dto.CategoryDTO;
import com.keee.inventario.entity.Category;
import com.keee.inventario.exception.ResourceNotFoundException;
import com.keee.inventario.repository.CategoryRepository;
import com.keee.inventario.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category createCategory(CategoryDTO categoryDTO) {
        Category category = Category.builder()
                .name(categoryDTO.getName())
                .description(categoryDTO.getDescription())
                .build();
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long id, CategoryDTO categoryDTO) {
        return categoryRepository.findById(id)
                .map(category -> {
                    category.setName(categoryDTO.getName());
                    category.setDescription(categoryDTO.getDescription());
                    return categoryRepository.save(category);
                })
                .orElseThrow(() -> new ResourceNotFoundException("La categoría con ID " + id + " no fue encontrada."));
    }

    @Override
    public Category patchCategory(Long id, CategoryDTO categoryDTO) {
        return categoryRepository.findById(id)
                .map(category -> {
                    if (categoryDTO.getName() != null) {
                        category.setName(categoryDTO.getName());
                    }
                    if (categoryDTO.getDescription() != null) {
                        category.setDescription(categoryDTO.getDescription());
                    }
                    return categoryRepository.save(category);
                })
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + id));
    }

    @Override
    public String deleteCategory(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return "Categoría eliminada correctamente.";
        } else {
            throw new ResourceNotFoundException("No se puede eliminar: La categoría con ID " + id + " no existe.");
        }
    }
}
