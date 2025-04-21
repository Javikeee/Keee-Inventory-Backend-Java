package com.keee.inventario.service.impl;

import com.keee.inventario.dto.RoleDTO;
import com.keee.inventario.dto.RoleRequestDTO;
import com.keee.inventario.dto.RoleResponseDTO;
import com.keee.inventario.entity.Permission;
import com.keee.inventario.entity.Role;
import com.keee.inventario.entity.RolePermission;
import com.keee.inventario.exception.ResourceNotFoundException;
import com.keee.inventario.helper.MessageHelper;
import com.keee.inventario.mapper.RoleMapper;
import com.keee.inventario.repository.PermissionRepository;
import com.keee.inventario.repository.RolePermissionRepository;
import com.keee.inventario.repository.RoleRepository;
import com.keee.inventario.service.RoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final RoleMapper roleMapper;
    private final MessageHelper messageHelper;

    @Override
    @Transactional
    public RoleResponseDTO createRole(RoleRequestDTO dto) {
        try {

            Role role = Role.builder()
                    .name(dto.getName())
                    .description(dto.getDescription())
                    .active(true)
                    .build();

            Role savedRole = roleRepository.save(role);

            List<Permission> permissions = permissionRepository.findAllById(dto.getPermissionIds());

            if (permissions.size() != dto.getPermissionIds().size()) {
                throw new IllegalArgumentException(messageHelper.getMessage("permissions.not_match", Locale.getDefault()));
            }

            permissions.forEach(permission -> {
                RolePermission rp = RolePermission.builder()
                        .role(savedRole)
                        .permission(permission)
                        .build();
                rolePermissionRepository.save(rp);
            });

            return roleMapper.entityToResponseDto(savedRole);
        } catch (DataAccessException e) {
            throw new RuntimeException(messageHelper.getMessage("role.error.create", Locale.getDefault()), e);
        }
    }

    @Override
    @Transactional
    public RoleResponseDTO updateRole(Long id, RoleRequestDTO dto) {
        try {
            Role role = roleRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            messageHelper.getMessage("role.error.not_found", Locale.getDefault()) + " " + id));

            role.setName(dto.getName());
            role.setDescription(dto.getDescription());

            // Eliminar relaciones anteriores
            Set<RolePermission> currentRolePermissions = role.getRolePermissions();
            rolePermissionRepository.deleteAll(currentRolePermissions);

            // Agregar nuevas
            List<Permission> newPermissions = permissionRepository.findAllById(dto.getPermissionIds());

            newPermissions.forEach(permission -> {
                RolePermission newRp = RolePermission.builder()
                        .role(role)
                        .permission(permission)
                        .build();
                rolePermissionRepository.save(newRp);
            });

            return roleMapper.entityToResponseDto(roleRepository.save(role));
        } catch (DataAccessException e) {
            throw new RuntimeException(messageHelper.getMessage("role.error.update", Locale.getDefault()), e);
        }
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        try {
            return roleRepository.findAll().stream()
                    .map(roleMapper::entityToDto)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new RuntimeException(messageHelper.getMessage("role.error.fetch", Locale.getDefault()), e);
        }
    }

    @Override
    public RoleResponseDTO getRoleById(Long id) {
        return roleRepository.findById(id)
                .map(roleMapper::entityToResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageHelper.getMessage("role.error.not_found", Locale.getDefault()) + " " + id));
    }

    @Override
    public void deleteRole(Long id) {
        try {
            Role role = roleRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            messageHelper.getMessage("role.error.not_found", Locale.getDefault()) + " " + id));
            roleRepository.delete(role);
        } catch (DataAccessException e) {
            throw new RuntimeException(messageHelper.getMessage("role.error.delete", Locale.getDefault()), e);
        }
    }
}
