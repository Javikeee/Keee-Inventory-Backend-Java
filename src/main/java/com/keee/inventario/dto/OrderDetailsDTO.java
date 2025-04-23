package com.keee.inventario.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDetailsDTO {

    private Long id;
    private Long productId;
    private String productName;
    private double productPriceAtMoment;
    private int quantity;
    private double totalPrice;

}
