package com.keee.inventario.mapper;

import com.keee.inventario.dto.PermissionDTO;
import com.keee.inventario.dto.RoleDTO;
import com.keee.inventario.dto.RoleRequestDTO;
import com.keee.inventario.dto.RoleResponseDTO;
import com.keee.inventario.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RoleMapper {

    private final PermissionMapper permissionMapper;

    public RoleResponseDTO entityToResponseDto(Role role) {
        List<PermissionDTO> permissions = role.getRolePermissions().stream()
                .map(rp -> permissionMapper.entityToDto(rp.getPermission()))
                .collect(Collectors.toList());

        return RoleResponseDTO.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .active(role.isActive())
                .permissions(permissions)
                .build();
    }

    public Role requestToEntity(RoleRequestDTO dto) {
        return Role.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .active(true)
                .build();
    }

    public RoleDTO entityToDto(Role role) {
        return RoleDTO.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .active(role.isActive())
                .build();
    }
}
