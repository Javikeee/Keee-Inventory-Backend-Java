package com.keee.inventario.dto;

import com.keee.inventario.entity.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class OrderResponseDTO {

    private Long id;
    private List<OrderDetailsResponseDTO> details;
    private Date date;
    private OrderStatus status;

}
