package com.keee.inventario.controller;

import com.keee.inventario.dto.SupplierRequestDTO;
import com.keee.inventario.dto.SupplierResponseDTO;
import com.keee.inventario.helper.MessageHelper;
import com.keee.inventario.service.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;
    private final MessageHelper messageHelper;

    @PostMapping
    public ResponseEntity<String> createCategory(
            @RequestBody @Valid SupplierRequestDTO supplierRequestDTO,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {

        supplierService.createSupplier(supplierRequestDTO);
        Locale locale = Locale.forLanguageTag(language);
        String message = messageHelper.getMessage("supplier.created", locale);
        return ResponseEntity.ok(message);

    }

    @GetMapping
    public List<SupplierResponseDTO> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponseDTO> getSupplierById(@PathVariable Long id) {
        return supplierService.getSupplierById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
