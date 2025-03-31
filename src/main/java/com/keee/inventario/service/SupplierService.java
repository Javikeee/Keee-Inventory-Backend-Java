package com.keee.inventario.service;

import com.keee.inventario.dto.SupplierRequestDTO;
import com.keee.inventario.dto.SupplierResponseDTO;
import com.keee.inventario.entity.Supplier;

import java.util.List;
import java.util.Optional;

public interface SupplierService {

    Supplier createSupplier(SupplierRequestDTO supplierRequestDTO);

    Optional<SupplierResponseDTO> getSupplierById(Long id);

    List<SupplierResponseDTO> getAllSuppliers();
}
