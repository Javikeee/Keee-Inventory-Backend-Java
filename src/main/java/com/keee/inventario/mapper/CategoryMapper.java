package com.keee.inventario.mapper;

import com.keee.inventario.dto.CategoryDTO;
import com.keee.inventario.dto.CategoryResponseDTO;
import com.keee.inventario.dto.CategoryRequestDTO;
import com.keee.inventario.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryMapper {

    public CategoryResponseDTO entityToResponseDto(Category category) {
        return CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .isActive(category.isActive())
                .build();
    }

    public Category requestDtoToEntity(CategoryRequestDTO dto) {
        return Category.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    public CategoryDTO entityToDto(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .isActive(category.isActive())
                .build();
    }

}
