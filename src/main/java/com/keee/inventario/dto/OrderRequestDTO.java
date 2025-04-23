package com.keee.inventario.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderRequestDTO {

    @NotNull(message = "El ID del usuario no puede ser nulo")
    private Long userId;

    @NotEmpty(message = "La lista de detalles no puede estar vacía")
    private List<OrderDetailsRequestDTO> details;

}
