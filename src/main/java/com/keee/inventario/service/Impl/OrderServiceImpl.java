package com.keee.inventario.service.impl;

import com.keee.inventario.dto.OrderDTO;
import com.keee.inventario.dto.OrderRequestDTO;
import com.keee.inventario.dto.OrderResponseDTO;
import com.keee.inventario.entity.Order;
import com.keee.inventario.entity.OrderDetails;
import com.keee.inventario.entity.OrderStatus;
import com.keee.inventario.entity.Product;
import com.keee.inventario.entity.User;
import com.keee.inventario.exception.ResourceNotFoundException;
import com.keee.inventario.helper.MessageHelper;
import com.keee.inventario.mapper.OrderMapper;
import com.keee.inventario.repository.OrderRepository;
import com.keee.inventario.repository.ProductRepository;
import com.keee.inventario.repository.UserRepository;
import com.keee.inventario.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;
    private final MessageHelper messageHelper;

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        try {
            return orderRepository.findAll().stream()
                    .map(orderMapper::entityToResponseDto)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new RuntimeException(messageHelper.getMessage("order.error.fetch", Locale.getDefault()), e);
        }
    }

    @Override
    public OrderResponseDTO getOrderById(Long id, Locale locale) {
        return orderRepository.findById(id)
                .map(orderMapper::entityToResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageHelper.getMessage("order.error.not_found", locale) + " " + id
                ));
    }

    @Override
    public List<OrderResponseDTO> getOrdersByUserId(Long userId) {
        try {
            return orderRepository.findByUserId(userId).stream()
                    .map(orderMapper::entityToResponseDto)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new RuntimeException(messageHelper.getMessage("order.error.fetch_by_user", Locale.getDefault()), e);
        }
    }

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {
        try {
            User user = userRepository.findById(orderRequestDTO.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

            Order order = Order.builder()
                    .user(user)
                    .date(new Date())
                    .status(OrderStatus.PENDING)
                    .build();

            List<OrderDetails> details = orderRequestDTO.getDetails().stream().map(detailDTO -> {
                Product product = productRepository.findById(detailDTO.getProductId())
                        .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + detailDTO.getProductId()));

                return OrderDetails.builder()
                        .order(order)
                        .product(product)
                        .quantity(detailDTO.getQuantity())
                        .totalPrice(product.getPrice() * detailDTO.getQuantity())
                        .build();
            }).collect(Collectors.toList());

            order.setDetails(details);
            Order saved = orderRepository.save(order);

            return orderMapper.entityToResponseDto(saved);
        } catch (DataAccessException e) {
            throw new RuntimeException(messageHelper.getMessage("order.error.create", Locale.getDefault()), e);
        }
    }


    @Override
    public OrderResponseDTO updateOrder(Long id, OrderDTO orderDTO) {
        return orderRepository.findById(id)
                .map(order -> {
                    if (Objects.nonNull(orderDTO.getDate())) {
                        order.setDate(orderDTO.getDate());
                    }
                    if (Objects.nonNull(orderDTO.getStatus())) {
                        order.setStatus(orderDTO.getStatus());
                    }
                    // Aquí podrías permitir actualizar detalles o el usuario si es necesario
                    Order updatedOrder = orderRepository.save(order);
                    return orderMapper.entityToResponseDto(updatedOrder);
                })
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageHelper.getMessage("order.error.not_found", Locale.getDefault()) + id));
    }

    @Override
    public OrderResponseDTO changeOrderStatus(Long id, OrderStatus status) {
        return orderRepository.findById(id)
                .map(order -> {
                    order.setStatus(status);
                    return orderMapper.entityToResponseDto(orderRepository.save(order));
                })
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageHelper.getMessage("order.error.not_found", Locale.getDefault()) + id));
    }
}
