package com.keee.inventario.service;

import com.keee.inventario.dto.SupplierDTO;
import com.keee.inventario.dto.SupplierRequestDTO;
import com.keee.inventario.dto.SupplierResponseDTO;

import java.util.List;
import java.util.Locale;

public interface SupplierService {

    List<SupplierResponseDTO> getAllSuppliers();

    SupplierResponseDTO getSupplierById(Long id, Locale locale);

    SupplierResponseDTO createSupplier(SupplierRequestDTO supplierRequestDTO);

    SupplierResponseDTO updateSupplierPartial(Long id, SupplierDTO supplierDTO);

    SupplierResponseDTO changeSupplierStatus(Long id, boolean isActive);
}