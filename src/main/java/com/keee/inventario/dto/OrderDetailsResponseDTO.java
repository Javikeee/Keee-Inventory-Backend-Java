package com.keee.inventario.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDetailsResponseDTO {

    private Long id;
    private Long productId;
    private String productName;
    private int quantity;
    private double totalPrice;

}
