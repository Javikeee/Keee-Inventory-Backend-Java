package com.keee.inventario.service;

import com.keee.inventario.dto.InvoiceResponseDTO;
import com.keee.inventario.entity.InvoiceStatus;

import java.util.List;
import java.util.Locale;

public interface InvoiceService {

    List<InvoiceResponseDTO> getAllInvoices();

    InvoiceResponseDTO getInvoiceById(Long id, Locale locale);

    InvoiceResponseDTO createInvoiceFromOrder(Long orderId);

    InvoiceResponseDTO changeInvoiceStatus(Long id, InvoiceStatus status);

    byte[] generateInvoicePdf(Long id);
}
