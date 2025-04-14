package com.keee.inventario.controller;

import com.keee.inventario.controller.entity.ApiResponse;
import com.keee.inventario.dto.PermissionDTO;
import com.keee.inventario.dto.PermissionRequestDTO;
import com.keee.inventario.dto.PermissionResponseDTO;
import com.keee.inventario.helper.MessageHelper;
import com.keee.inventario.service.PermissionService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;
    private final MessageHelper messageHelper;

    @PostMapping
    public ResponseEntity<ApiResponse<PermissionResponseDTO>> createPermission(@RequestBody PermissionRequestDTO dto,
                                                                               @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {
        Locale locale = Locale.forLanguageTag(language);
        PermissionResponseDTO created = permissionService.createPermission(dto);
        String message = messageHelper.getMessage("permission.created", locale);
        return ResponseEntity.ok(new ApiResponse<>(message, created));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PermissionDTO>>> getAllPermission(
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {
        Locale locale = Locale.forLanguageTag(language);
        List<PermissionDTO> list = permissionService.getAllPermission();
        String message = messageHelper.getMessage("permission.list", locale);
        return ResponseEntity.ok(new ApiResponse<>(message, list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PermissionDTO>> getPermissionById(@PathVariable Long id,
                                                                        @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {
        Locale locale = Locale.forLanguageTag(language);
        PermissionDTO found = permissionService.getPermissionById(id);
        String message = messageHelper.getMessage("permission.found", locale);
        return ResponseEntity.ok(new ApiResponse<>(message, found));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PermissionDTO>> updatePermission(@PathVariable Long id,
                                                                               @RequestBody PermissionDTO dto,
                                                                               @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {
        Locale locale = Locale.forLanguageTag(language);
        PermissionDTO updated = permissionService.updatePermission(id, dto);
        String message = messageHelper.getMessage("permission.updated", locale);
        return ResponseEntity.ok(new ApiResponse<>(message, updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePermission(@PathVariable Long id,
                                                              @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {
        Locale locale = Locale.forLanguageTag(language);
        permissionService.deletePermission(id);
        String message = messageHelper.getMessage("permission.deleted", locale);
        return ResponseEntity.ok(new ApiResponse<>(message, null));
    }
}
