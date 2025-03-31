package com.keee.inventario.service;

import com.keee.inventario.dto.SupplierRequestDTO;
import com.keee.inventario.entity.Supplier;

public interface SupplierService {
    Supplier createSupplier(SupplierRequestDTO supplierRequestDTO);
}
