package com.keee.inventario.service;

import com.keee.inventario.dto.PermissionDTO;
import com.keee.inventario.dto.PermissionRequestDTO;
import com.keee.inventario.dto.PermissionResponseDTO;

import java.util.List;

public interface PermissionService {
    PermissionResponseDTO createPermission(PermissionRequestDTO dto);

    List<PermissionDTO> getAllPermission();

    PermissionDTO getPermissionById(Long id);

    PermissionDTO updatePermission(Long id, PermissionDTO dto);

    void deletePermission(Long id);
}
