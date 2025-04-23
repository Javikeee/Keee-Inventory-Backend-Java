package com.keee.inventario.service;

import com.keee.inventario.dto.OrderDTO;
import com.keee.inventario.dto.OrderRequestDTO;
import com.keee.inventario.dto.OrderResponseDTO;
import com.keee.inventario.entity.OrderStatus;

import java.util.List;
import java.util.Locale;

public interface OrderService {

    List<OrderResponseDTO> getAllOrders();

    OrderResponseDTO getOrderById(Long id, Locale locale);

    List<OrderResponseDTO> getOrdersByUserId(Long userId);

    OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO);

    OrderResponseDTO updateOrder(Long id, OrderDTO orderDTO);

    OrderResponseDTO changeOrderStatus(Long id, OrderStatus status);

}
