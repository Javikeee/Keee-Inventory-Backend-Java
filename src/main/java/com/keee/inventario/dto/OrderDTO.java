package com.keee.inventario.dto;

import com.keee.inventario.entity.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class OrderDTO {

    private Long id;
    private Date date;
    private OrderStatus status;
    private List<OrderDetailsDTO> details;
    private UserDTO user;

}
