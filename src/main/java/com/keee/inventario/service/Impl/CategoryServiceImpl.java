package com.keee.inventario.service.impl;

import com.keee.inventario.dto.CategoryDTO;
import com.keee.inventario.dto.CategoryRequestDTO;
import com.keee.inventario.dto.CategoryResponseDTO;
import com.keee.inventario.entity.Category;
import com.keee.inventario.exception.ResourceNotFoundException;
import com.keee.inventario.helper.MessageHelper;
import com.keee.inventario.mapper.CategoryMapper;
import com.keee.inventario.repository.CategoryRepository;
import com.keee.inventario.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final MessageHelper messageHelper;

    @Override
    public List<CategoryResponseDTO> getAllCategories() {
        try {
            return categoryRepository.findAll()
                    .stream()
                    .map(categoryMapper::entityToResponseDto)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new RuntimeException(messageHelper.getMessage("supplier.error.fetch", Locale.getDefault()), e);
        }
    }

    @Override
    public CategoryResponseDTO getCategoryById(Long id, Locale locale) {
        return categoryRepository.findById(id)
                .map(categoryMapper::entityToResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageHelper.getMessage("category.error.not_found", locale) + " " + id
                ));
    }

    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        try {
            Category category = categoryMapper.requestDtoToEntity(categoryRequestDTO);
            return categoryMapper.entityToResponseDto(categoryRepository.save(category));
        } catch (DataAccessException e) {
            throw new RuntimeException(messageHelper.getMessage("category.error.create", Locale.getDefault()), e);
        }
    }

    @Override
    public CategoryResponseDTO updateCategoryPartial(Long id, CategoryDTO categoryDTO) {
        return categoryRepository.findById(id)
                .map(category -> {
                    if (Objects.nonNull(categoryDTO.getName())) {
                        category.setName(categoryDTO.getName());
                    }
                    if (Objects.nonNull(categoryDTO.getDescription())) {
                        category.setDescription(categoryDTO.getDescription());
                    }
                    Category updatedCategory = categoryRepository.save(category);
                    return categoryMapper.entityToResponseDto(updatedCategory);
                })
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageHelper.getMessage("category.error.not_found", Locale.getDefault()) + id));
    }

    @Override
    public CategoryResponseDTO changeCategoryStatus(Long id, boolean isActive) {
        return categoryRepository.findById(id)
                .map(category -> {
                    category.setActive(isActive);
                    Category updatedCategory = categoryRepository.save(category);
                    return categoryMapper.entityToResponseDto(updatedCategory);
                })
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageHelper.getMessage("supplier.error.not_found", Locale.getDefault()) + id));
    }


}
