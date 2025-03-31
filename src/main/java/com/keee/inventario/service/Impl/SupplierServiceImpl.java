package com.keee.inventario.service.impl;

import com.keee.inventario.dto.SupplierRequestDTO;
import com.keee.inventario.entity.Supplier;
import com.keee.inventario.mapper.SupplierMapper;
import com.keee.inventario.repository.SupplierRepository;
import com.keee.inventario.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    SupplierRepository supplierRepository;
    SupplierMapper supplierMapper;

    @Override
    public Supplier createSupplier(SupplierRequestDTO supplierRequestDTO) {
        Supplier supplier = supplierMapper.supplierRequestDtoToSupplier(supplierRequestDTO);
        return supplierRepository.save(supplier);
    }

}
