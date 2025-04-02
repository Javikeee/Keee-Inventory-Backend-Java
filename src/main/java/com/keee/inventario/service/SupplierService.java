package com.keee.inventario.service;

import com.keee.inventario.dto.SupplierDTO;
import com.keee.inventario.dto.SupplierRequestDTO;
import com.keee.inventario.dto.SupplierResponseDTO;

import java.util.List;
import java.util.Optional;

public interface SupplierService {

    SupplierResponseDTO createSupplier(SupplierRequestDTO supplierRequestDTO);

    Optional<SupplierResponseDTO> getSupplierById(Long id);

    List<SupplierResponseDTO> getAllSuppliers();

    SupplierResponseDTO updateSupplierPartial(Long id, SupplierDTO supplierDTO);

    SupplierResponseDTO changeSupplierStatus(Long id, boolean isActive);
}