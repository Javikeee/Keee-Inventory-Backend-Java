package com.keee.inventario.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDetailsRequestDTO {

    @NotNull(message = "El ID del producto no puede ser nulo")
    private Long productId;

    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private int quantity;

}
