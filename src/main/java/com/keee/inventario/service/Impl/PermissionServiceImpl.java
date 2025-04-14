package com.keee.inventario.service.impl;

import com.keee.inventario.dto.PermissionDTO;
import com.keee.inventario.dto.PermissionRequestDTO;
import com.keee.inventario.dto.PermissionResponseDTO;
import com.keee.inventario.entity.Permission;
import com.keee.inventario.helper.MessageHelper;
import com.keee.inventario.mapper.PermissionMapper;
import com.keee.inventario.repository.PermissionRepository;
import com.keee.inventario.service.PermissionService;
import com.keee.inventario.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;
    private final MessageHelper messageHelper;

    @Override
    public PermissionResponseDTO createPermission(PermissionRequestDTO dto) {
        try {
            Permission permission = permissionMapper.requestToEntity(dto);
            permission.setActive(true);
            return permissionMapper.entityToResponseDto(permissionRepository.save(permission));
        }catch (DataAccessException e){
            throw new RuntimeException(messageHelper.getMessage("permission.error.create", Locale.getDefault()), e);
        }
    }

    @Override
    public List<PermissionDTO> getAllPermission() {
        try {
            return permissionRepository.findAll().stream()
                    .map(permissionMapper::entityToDto)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new RuntimeException(messageHelper.getMessage("permission.error.fetch", Locale.getDefault()), e);
        }
    }

    @Override
    public PermissionDTO getPermissionById(Long id) {
        return permissionRepository.findById(id)
                .map(permissionMapper::entityToDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageHelper.getMessage("permission.error.not_found", Locale.getDefault()) + id));
    }

    @Override
    public PermissionDTO updatePermission(Long id, PermissionDTO dto) {
        return permissionRepository.findById(id)
                .map(permission -> {
                    permission.setName(dto.getName());
                    return permissionMapper.entityToDto(permissionRepository.save(permission));
                }).orElseThrow(() -> new ResourceNotFoundException(
                        messageHelper.getMessage("permission.error.not_found", Locale.getDefault()) + id));
    }

    @Override
    public void deletePermission(Long id) {
        if (!permissionRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    messageHelper.getMessage("permission.error.not_found", Locale.getDefault()) + id);
        }
        permissionRepository.deleteById(id);
    }
}
