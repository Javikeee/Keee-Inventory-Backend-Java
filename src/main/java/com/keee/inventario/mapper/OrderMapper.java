package com.keee.inventario.mapper;

import com.keee.inventario.dto.OrderDTO;
import com.keee.inventario.dto.OrderDetailsResponseDTO;
import com.keee.inventario.dto.OrderRequestDTO;
import com.keee.inventario.dto.OrderResponseDTO;
import com.keee.inventario.dto.OrderDetailsDTO;
import com.keee.inventario.entity.Order;
import com.keee.inventario.entity.OrderDetails;
import com.keee.inventario.entity.Product;
import com.keee.inventario.entity.User;
import com.keee.inventario.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final ProductRepository productRepository;

    public OrderResponseDTO entityToResponseDto(Order order) {
        return OrderResponseDTO.builder()
                .id(order.getId())
                .date(order.getDate())
                .status(order.getStatus())
                .details(detailsToDtoResponse(order.getDetails()))
                .build();
    }

    public OrderDTO entityToDto(Order order) {
        User user = order.getUser();

        return OrderDTO.builder()
                .id(order.getId())
                .date(order.getDate())
                .status(order.getStatus())
                .details(detailsToDto(order.getDetails()))
                .build();
    }

    public Order requestDtoToEntity(OrderRequestDTO dto, User user) {
        Order order = Order.builder()
                .user(user)
                .date(new Date())
                .status(null) // El status lo puedes setear en el servicio
                .build();

        List<OrderDetails> details = dto.getDetails().stream().map(detailDto -> {
            Product product = productRepository.findById(detailDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + detailDto.getProductId()));

            return OrderDetails.builder()
                    .order(order) // importante para relación bidireccional
                    .product(product)
                    .quantity(detailDto.getQuantity())
                    .totalPrice(product.getPrice() * detailDto.getQuantity())
                    .build();
        }).collect(Collectors.toList());

        order.setDetails(details);
        return order;
    }

    public List<OrderDetailsDTO> detailsToDto(List<OrderDetails> detailsList) {
        return detailsList.stream()
                .map(this::detailToDto)
                .collect(Collectors.toList());
    }

    public List<OrderDetailsResponseDTO> detailsToDtoResponse(List<OrderDetails> detailsList) {
        return detailsList.stream()
                .map(this::detailToDtoResponse)
                .collect(Collectors.toList());
    }

    public OrderDetailsDTO detailToDto(OrderDetails detail) {
        Product product = detail.getProduct();
        return OrderDetailsDTO.builder()
                .id(detail.getId())
                .productId(product.getId())
                .productName(product.getName())
                .productPriceAtMoment(detail.getTotalPrice() / detail.getQuantity()) // El precio unitario se obtiene así
                .quantity(detail.getQuantity())
                .totalPrice(detail.getTotalPrice())
                .build();
    }

    public OrderDetailsResponseDTO detailToDtoResponse(OrderDetails detail) {
        Product product = detail.getProduct();
        return OrderDetailsResponseDTO.builder()
                .id(detail.getId())
                .productId(product.getId())
                .productName(product.getName())
                .quantity(detail.getQuantity())
                .totalPrice(detail.getTotalPrice())
                .build();
    }
}
