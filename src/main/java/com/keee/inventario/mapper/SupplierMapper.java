package com.keee.inventario.mapper;

import com.keee.inventario.dto.SupplierRequestDTO;
import com.keee.inventario.entity.Supplier;

public class SupplierMapper {

    public Supplier supplierRequestDtoToSupplier(SupplierRequestDTO supplierRequestDTO) {

        Supplier supplier = Supplier.builder()
                .companyName(supplierRequestDTO.getCompanyName())
                .contactName(supplierRequestDTO.getContactName())
                .telephone(supplierRequestDTO.getTelephone())
                .email(supplierRequestDTO.getEmail())
                .build();

        return supplier;
    }
}
