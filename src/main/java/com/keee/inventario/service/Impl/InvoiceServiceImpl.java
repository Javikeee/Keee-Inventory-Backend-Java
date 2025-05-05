package com.keee.inventario.service.impl;

import com.keee.inventario.dto.InvoiceResponseDTO;
import com.keee.inventario.entity.Invoice;
import com.keee.inventario.entity.InvoiceStatus;
import com.keee.inventario.entity.Order;
import com.keee.inventario.exception.ResourceNotFoundException;
import com.keee.inventario.helper.MessageHelper;
import com.keee.inventario.mapper.InvoiceMapper;
import com.keee.inventario.repository.InvoiceRepository;
import com.keee.inventario.repository.OrderRepository;
import com.keee.inventario.service.InvoiceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final OrderRepository orderRepository;
    private final InvoiceMapper invoiceMapper;
    private final MessageHelper messageHelper;

    @Override
    public List<InvoiceResponseDTO> getAllInvoices() {
        return invoiceRepository.findAll()
                .stream()
                .map(invoiceMapper::entityToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceResponseDTO getInvoiceById(Long id, Locale locale) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(messageHelper.getMessage("invoice.not_found", Locale.getDefault())));

        return invoiceMapper.entityToResponseDto(invoice);
    }

    @Override
    @Transactional
    public InvoiceResponseDTO createInvoiceFromOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("order.not.found"));

        Invoice invoice = Invoice.builder()
                .order(order)
                .date(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())                )
                .total(calculateTotal(order))
                .status(InvoiceStatus.COMPLETED)
                .build();

        invoice = invoiceRepository.save(invoice);

        return invoiceMapper.entityToResponseDto(invoice);
    }

    @Override
    public InvoiceResponseDTO changeInvoiceStatus(Long id, InvoiceStatus status) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("invoice.not.found"));

        invoice.setStatus(InvoiceStatus.valueOf(status.name().toUpperCase()));
        Invoice updated = invoiceRepository.save(invoice);
        return invoiceMapper.entityToResponseDto(updated);
    }

    @Override
    public byte[] generateInvoicePdf(Long id) {
        // Pendiente de implementar con iText o Apache PDFBox
        return new byte[0];
    }

    private double calculateTotal(Order order) {
        return order.getDetails().stream()
                .mapToDouble(d -> d.getTotalPrice())
                .sum();
    }
}
