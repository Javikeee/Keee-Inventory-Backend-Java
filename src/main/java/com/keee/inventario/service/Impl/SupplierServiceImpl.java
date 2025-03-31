package com.keee.inventario.service.impl;

import com.keee.inventario.dto.SupplierRequestDTO;
import com.keee.inventario.dto.SupplierResponseDTO;
import com.keee.inventario.entity.Supplier;
import com.keee.inventario.exception.ResourceNotFoundException;
import com.keee.inventario.mapper.SupplierMapper;
import com.keee.inventario.repository.SupplierRepository;
import com.keee.inventario.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    SupplierRepository supplierRepository;
    SupplierMapper supplierMapper;

    @Override
    public Supplier createSupplier(SupplierRequestDTO supplierRequestDTO) {
        Supplier supplier = supplierMapper.requestDtoToEntity(supplierRequestDTO);
        return supplierRepository.save(supplier);
    }

    @Override
    public Optional<SupplierResponseDTO> getSupplierById(Long id) {
        Optional<Supplier> supplier = supplierRepository.findById(id);

        if (supplier.isEmpty()) {
            return Optional.empty();
        }

        SupplierResponseDTO supplierResponseDTO = supplierMapper.entityToResponseDto(supplier.get());
        return Optional.of(supplierResponseDTO);
    }


    @Override
    public List<SupplierResponseDTO> getAllSuppliers() {
        try {
            List<Supplier> supplierList = supplierRepository.findAll();
            return supplierList.stream()
                    .map(supplierMapper::entityToResponseDto)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new ResourceNotFoundException("Error accessing suppliers data: " + e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred while fetching suppliers", e);
        }
    }

}
