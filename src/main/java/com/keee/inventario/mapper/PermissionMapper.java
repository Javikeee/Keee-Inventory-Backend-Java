package com.keee.inventario.mapper;

import com.keee.inventario.dto.PermissionDTO;
import com.keee.inventario.dto.PermissionRequestDTO;
import com.keee.inventario.dto.PermissionResponseDTO;
import com.keee.inventario.entity.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PermissionMapper {

    public Permission dtoToEntity(PermissionDTO dto) {
        return Permission.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .active(dto.isActive())
                .build();
    }

    public Permission requestToEntity(PermissionRequestDTO dto) {
        return Permission.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .active(true)
                .build();
    }

    public PermissionResponseDTO entityToResponseDto(Permission entity) {
        return PermissionResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .active(entity.isActive())
                .build();
    }

    public PermissionDTO entityToDto(Permission permission) {
        return PermissionDTO.builder()
                .id(permission.getId())
                .name(permission.getName())
                .description(permission.getDescription())
                .active(permission.isActive())
                .build();
    }
}
