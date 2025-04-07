package com.keee.inventario.controller;

import com.keee.inventario.controller.entity.ApiResponse;
import com.keee.inventario.dto.SupplierDTO;
import com.keee.inventario.dto.SupplierRequestDTO;
import com.keee.inventario.dto.SupplierResponseDTO;
import com.keee.inventario.helper.MessageHelper;
import com.keee.inventario.service.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;
    private final MessageHelper messageHelper;

    /**
     * Method to get all the suppliers
     *
     * @param language
     * @return
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<SupplierResponseDTO>>> getAllSuppliers(
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {

        Locale locale = Locale.forLanguageTag(language);
        List<SupplierResponseDTO> suppliers = supplierService.getAllSuppliers();
        String message = messageHelper.getMessage("supplier.list", locale);
        return ResponseEntity.ok(new ApiResponse<>(message, suppliers));
    }

    /**
     * Method to get a supplier
     *
     * @param id
     * @param language
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SupplierResponseDTO>> getSupplierById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {

        Locale locale = Locale.forLanguageTag(language);
        SupplierResponseDTO supplierResponseDTO = supplierService.getSupplierById(id, locale);
        String message = messageHelper.getMessage("supplier.found", locale);
        return ResponseEntity.ok(new ApiResponse<>(message, supplierResponseDTO));
    }

    /**
     * Method to create a new supplier
     *
     * @param supplierRequestDTO
     * @param language
     * @return
     */
    @PostMapping
    public ResponseEntity<ApiResponse<SupplierResponseDTO>> createSupplier(
            @RequestBody @Valid SupplierRequestDTO supplierRequestDTO,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {

        Locale locale = Locale.forLanguageTag(language);
        SupplierResponseDTO createdSupplier = supplierService.createSupplier(supplierRequestDTO);
        String message = messageHelper.getMessage("supplier.created", locale);
        return ResponseEntity.ok(new ApiResponse<>(message, createdSupplier));
    }

    /**
     * Method to change supplier status
     *
     * Un proveedor nunca podrá ser borrado, solo dado de baja, ya que eliminar al proveedor
     * perderemos la información de sus productos, que tienen relación con los pedidos y las facturas
     * de un Usuario.
     *
     * @param id
     * @param isActive
     * @param language
     * @return
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<SupplierResponseDTO>> changeSupplierStatus(
            @PathVariable Long id,
            @RequestParam boolean isActive,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {

        Locale locale = Locale.forLanguageTag(language);
        SupplierResponseDTO updatedSupplier = supplierService.changeSupplierStatus(id, isActive);
        String messageKey = isActive ? "supplier.activated" : "supplier.deactivated";
        String message = messageHelper.getMessage(messageKey, locale);
        return ResponseEntity.ok(new ApiResponse<>(message, updatedSupplier));
    }

    /**
     * Method to update supplier info
     *
     * @param id
     * @param supplierDTO
     * @param language
     * @return
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<SupplierResponseDTO>> updateSupplier(
            @PathVariable Long id,
            @RequestBody @Valid SupplierDTO supplierDTO,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {

        Locale locale = Locale.forLanguageTag(language);
        SupplierResponseDTO updatedSupplier = supplierService.updateSupplierPartial(id, supplierDTO);
        String message = messageHelper.getMessage("supplier.updated", locale);
        return ResponseEntity.ok(new ApiResponse<>(message, updatedSupplier));
    }
}
