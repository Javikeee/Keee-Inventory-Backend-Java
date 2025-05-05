package com.keee.inventario.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceResponseDTO {
    private Long id;
    private String invoiceNumber;
    private LocalDate date;
    private Double totalAmount;
    private String status;
    private Long orderId;
    private List<InvoiceDetailDTO> details;
}
