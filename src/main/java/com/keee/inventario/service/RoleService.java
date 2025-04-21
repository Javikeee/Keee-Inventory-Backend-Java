package com.keee.inventario.service;

import com.keee.inventario.dto.RoleDTO;
import com.keee.inventario.dto.RoleRequestDTO;
import com.keee.inventario.dto.RoleResponseDTO;

import java.util.List;

public interface RoleService {

    RoleResponseDTO createRole(RoleRequestDTO dto);
    RoleResponseDTO updateRole(Long id, RoleRequestDTO dto);
    List<RoleDTO> getAllRoles();
    RoleResponseDTO getRoleById(Long id);
    void deleteRole(Long id);

}
