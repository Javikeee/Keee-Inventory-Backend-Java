package com.keee.inventario.mapper;

import com.keee.inventario.dto.InvoiceDetailDTO;
import com.keee.inventario.dto.InvoiceResponseDTO;
import com.keee.inventario.entity.Invoice;
import com.keee.inventario.entity.OrderDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InvoiceMapper {

    public InvoiceResponseDTO entityToResponseDto(Invoice invoice) {
        return InvoiceResponseDTO.builder()
                .id(invoice.getId())
                .date(invoice.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate())
                .totalAmount(invoice.getTotal())
                .status(invoice.getStatus().name())
                .orderId(invoice.getOrder().getId())
                .details(mapDetails(invoice.getOrder().getDetails()))
                .build();
    }

    private List<InvoiceDetailDTO> mapDetails(List<OrderDetails> details) {
        return details.stream().map(d ->
                InvoiceDetailDTO.builder()
                        .productName(d.getProduct().getName())
                        .quantity(d.getQuantity())
                        .unitPrice(d.getTotalPrice() / d.getQuantity())
                        .totalPrice(d.getTotalPrice())
                        .build()
        ).collect(Collectors.toList());
    }
}
