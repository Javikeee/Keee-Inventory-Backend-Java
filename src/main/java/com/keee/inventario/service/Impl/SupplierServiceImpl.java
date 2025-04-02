package com.keee.inventario.service.impl;

import com.keee.inventario.dto.SupplierDTO;
import com.keee.inventario.dto.SupplierRequestDTO;
import com.keee.inventario.dto.SupplierResponseDTO;
import com.keee.inventario.entity.Supplier;
import com.keee.inventario.exception.ResourceNotFoundException;
import com.keee.inventario.helper.MessageHelper;
import com.keee.inventario.mapper.SupplierMapper;
import com.keee.inventario.repository.SupplierRepository;
import com.keee.inventario.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;
    private final MessageHelper messageHelper;

    @Override
    public SupplierResponseDTO createSupplier(SupplierRequestDTO supplierRequestDTO) {
        try {
            Supplier supplier = supplierMapper.requestDtoToEntity(supplierRequestDTO);
            return supplierMapper.entityToResponseDto(supplierRepository.save(supplier));
        } catch (DataAccessException e) {
            throw new RuntimeException(messageHelper.getMessage("supplier.error.create", Locale.getDefault()), e);
        }
    }

    @Override
    public Optional<SupplierResponseDTO> getSupplierById(Long id) {
        return supplierRepository.findById(id)
                .map(supplierMapper::entityToResponseDto);
    }

    @Override
    public List<SupplierResponseDTO> getAllSuppliers() {
        try {
            return supplierRepository.findAll()
                    .stream()
                    .map(supplierMapper::entityToResponseDto)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new RuntimeException(messageHelper.getMessage("supplier.error.fetch", Locale.getDefault()), e);
        }
    }

    @Override
    public SupplierResponseDTO updateSupplierPartial(Long id, SupplierDTO supplierDTO) {
        return supplierRepository.findById(id)
                .map(supplier -> {
                    if (Objects.nonNull(supplierDTO.getCompanyName())) {
                        supplier.setCompanyName(supplierDTO.getCompanyName());
                    }
                    if (Objects.nonNull(supplierDTO.getContactName())) {
                        supplier.setContactName(supplierDTO.getContactName());
                    }
                    if (Objects.nonNull(supplierDTO.getTelephone())) {
                        supplier.setTelephone(supplierDTO.getTelephone());
                    }
                    if (Objects.nonNull(supplierDTO.getEmail())) {
                        supplier.setEmail(supplierDTO.getEmail());
                    }
                    Supplier updatedSupplier = supplierRepository.save(supplier);
                    return supplierMapper.entityToResponseDto(updatedSupplier);
                })
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageHelper.getMessage("supplier.error.not_found", Locale.getDefault()) + id));
    }

    @Override
    public SupplierResponseDTO changeSupplierStatus(Long id, boolean isActive) {
        return supplierRepository.findById(id)
                .map(supplier -> {
                    supplier.setActive(isActive);
                    Supplier updatedSupplier = supplierRepository.save(supplier);
                    return supplierMapper.entityToResponseDto(updatedSupplier);
                })
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageHelper.getMessage("supplier.error.not_found", Locale.getDefault()) + id));
    }
}