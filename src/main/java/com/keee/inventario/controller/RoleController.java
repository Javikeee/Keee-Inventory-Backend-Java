package com.keee.inventario.controller;

import com.keee.inventario.controller.entity.ApiResponse;
import com.keee.inventario.dto.RoleDTO;
import com.keee.inventario.dto.RoleRequestDTO;
import com.keee.inventario.dto.RoleResponseDTO;
import com.keee.inventario.helper.MessageHelper;
import com.keee.inventario.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;
    private final MessageHelper messageHelper;

    @GetMapping
    public ResponseEntity<ApiResponse<List<RoleDTO>>> getAllRoles(
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {

        Locale locale = Locale.forLanguageTag(language);
        List<RoleDTO> roles = roleService.getAllRoles();
        String message = messageHelper.getMessage("role.list", locale);
        return ResponseEntity.ok(new ApiResponse<>(message, roles));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponseDTO>> getRoleById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {

        Locale locale = Locale.forLanguageTag(language);
        RoleResponseDTO role = roleService.getRoleById(id);
        String message = messageHelper.getMessage("role.found", locale);
        return ResponseEntity.ok(new ApiResponse<>(message, role));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<RoleResponseDTO>> createRole(
            @RequestBody @Valid RoleRequestDTO dto,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {

        Locale locale = Locale.forLanguageTag(language);
        RoleResponseDTO role = roleService.createRole(dto);
        String message = messageHelper.getMessage("role.created", locale);
        return ResponseEntity.ok(new ApiResponse<>(message, role));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponseDTO>> updateRole(
            @PathVariable Long id,
            @RequestBody @Valid RoleRequestDTO dto,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {

        Locale locale = Locale.forLanguageTag(language);
        RoleResponseDTO updated = roleService.updateRole(id, dto);
        String message = messageHelper.getMessage("role.updated", locale);
        return ResponseEntity.ok(new ApiResponse<>(message, updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteRole(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {

        Locale locale = Locale.forLanguageTag(language);
        roleService.deleteRole(id);
        String message = messageHelper.getMessage("role.deleted", locale);
        return ResponseEntity.ok(new ApiResponse<>(message, null));
    }
}
