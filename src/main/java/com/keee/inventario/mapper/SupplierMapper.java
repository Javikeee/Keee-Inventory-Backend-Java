package com.keee.inventario.mapper;

import com.keee.inventario.dto.SupplierRequestDTO;
import com.keee.inventario.dto.SupplierResponseDTO;
import com.keee.inventario.entity.Supplier;

public class SupplierMapper {

    public Supplier requestDtoToEntity(SupplierRequestDTO supplierRequestDTO) {

        return Supplier.builder()
                .companyName(supplierRequestDTO.getCompanyName())
                .contactName(supplierRequestDTO.getContactName())
                .telephone(supplierRequestDTO.getTelephone())
                .email(supplierRequestDTO.getEmail())
                .build();
    }

    public SupplierResponseDTO entityToResponseDto(Supplier supplier) {

        return SupplierResponseDTO.builder()
                .id(supplier.getId())
                .companyName(supplier.getCompanyName())
                .contactName(supplier.getContactName())
                .telephone(supplier.getTelephone())
                .email(supplier.getEmail())
                .isActive(supplier.isActive())
                .build();

    }
}
