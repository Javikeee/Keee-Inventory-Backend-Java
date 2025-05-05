package com.keee.inventario.controller;

import com.keee.inventario.controller.entity.ApiResponse;
import com.keee.inventario.dto.InvoiceResponseDTO;
import com.keee.inventario.entity.InvoiceStatus;
import com.keee.inventario.helper.MessageHelper;
import com.keee.inventario.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final MessageHelper messageHelper;

    /**
     * Method to get all invoices
     *
     * @param language
     * @return
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<InvoiceResponseDTO>>> getAllInvoices(
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {
        Locale locale = Locale.forLanguageTag(language);
        List<InvoiceResponseDTO> invoices = invoiceService.getAllInvoices();
        String message = messageHelper.getMessage("invoice.list", locale);
        return ResponseEntity.ok(new ApiResponse<>(message, invoices));
    }

    /**
     * Method to get an invoice by id
     *
     * @param id
     * @param language
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InvoiceResponseDTO>> getInvoiceById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {
        Locale locale = Locale.forLanguageTag(language);
        InvoiceResponseDTO invoice = invoiceService.getInvoiceById(id, locale);
        String message = messageHelper.getMessage("invoice.found", locale);
        return ResponseEntity.ok(new ApiResponse<>(message, invoice));
    }

    /**
     * Method to create an invoice
     *
     * @param orderId
     * @param language
     * @return
     */
    @PostMapping
    public ResponseEntity<ApiResponse<InvoiceResponseDTO>> createInvoice(
            @RequestParam Long orderId,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {
        Locale locale = Locale.forLanguageTag(language);
        InvoiceResponseDTO invoice = invoiceService.createInvoiceFromOrder(orderId);
        String message = messageHelper.getMessage("invoice.created", locale);
        return ResponseEntity.ok(new ApiResponse<>(message, invoice));
    }

    /**
     * Method to change status from invoice
     *
     * @param id
     * @param status
     * @param language
     * @return
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<InvoiceResponseDTO>> changeInvoiceStatus(
            @PathVariable Long id,
            @RequestParam InvoiceStatus status,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {
        Locale locale = Locale.forLanguageTag(language);
        InvoiceResponseDTO updated = invoiceService.changeInvoiceStatus(id, status);
        String message = messageHelper.getMessage("invoice.status.updated", locale);
        return ResponseEntity.ok(new ApiResponse<>(message, updated));
    }

    /**
     * Method to get pdf from an invoice by id
     * TODO
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> downloadInvoicePdf(@PathVariable Long id) {
        byte[] pdf = invoiceService.generateInvoicePdf(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "invoice-" + id + ".pdf");

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }
}

