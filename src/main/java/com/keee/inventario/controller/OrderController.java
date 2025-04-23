package com.keee.inventario.controller;

import com.keee.inventario.controller.entity.ApiResponse;
import com.keee.inventario.dto.OrderDTO;
import com.keee.inventario.dto.OrderRequestDTO;
import com.keee.inventario.dto.OrderResponseDTO;
import com.keee.inventario.entity.OrderStatus;
import com.keee.inventario.helper.MessageHelper;
import com.keee.inventario.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MessageHelper messageHelper;

    /**
     * Method to get all orders
     *
     * @param language
     * @return
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponseDTO>>> getAllOrders(
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {

        Locale locale = Locale.forLanguageTag(language);
        List<OrderResponseDTO> orders = orderService.getAllOrders();
        String message = messageHelper.getMessage("order.list", locale);
        return ResponseEntity.ok(new ApiResponse<>(message, orders));
    }

    /**
     * Method to get a specific order by ID
     *
     * @param id
     * @param language
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponseDTO>> getOrderById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {

        Locale locale = Locale.forLanguageTag(language);
        OrderResponseDTO order = orderService.getOrderById(id, locale);
        String message = messageHelper.getMessage("order.found", locale);
        return ResponseEntity.ok(new ApiResponse<>(message, order));
    }

    /**
     * Method to get the history of orders for a specific user
     *
     * @param userId
     * @param language
     * @return
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<OrderResponseDTO>>> getOrdersByUserId(
            @PathVariable Long userId,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {

        Locale locale = Locale.forLanguageTag(language);
        List<OrderResponseDTO> orders = orderService.getOrdersByUserId(userId);
        String message = messageHelper.getMessage("order.list.user", locale);
        return ResponseEntity.ok(new ApiResponse<>(message, orders));
    }

    /**
     * Method to create a new order
     *
     * @param orderRequestDTO
     * @param language
     * @return
     */
    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponseDTO>> createOrder(
            @Valid @RequestBody OrderRequestDTO orderRequestDTO,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {

        Locale locale = Locale.forLanguageTag(language);
        OrderResponseDTO createdOrder = orderService.createOrder(orderRequestDTO);
        String message = messageHelper.getMessage("order.created", locale);
        return ResponseEntity.ok(new ApiResponse<>(message, createdOrder));
    }

    /**
     * Method to update order partially (date and status)
     *
     * @param id
     * @param orderDTO
     * @param language
     * @return
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponseDTO>> updateOrder(
            @PathVariable Long id,
            @Valid @RequestBody OrderDTO orderDTO,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {

        Locale locale = Locale.forLanguageTag(language);
        OrderResponseDTO updatedOrder = orderService.updateOrder(id, orderDTO);
        String message = messageHelper.getMessage("order.updated", locale);
        return ResponseEntity.ok(new ApiResponse<>(message, updatedOrder));
    }

    /**
     * Method to change order status (PENDING, COMPLETED, CANCELLED...)
     *
     * @param id
     * @param status
     * @param language
     * @return
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<OrderResponseDTO>> changeOrderStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {

        Locale locale = Locale.forLanguageTag(language);
        OrderResponseDTO updatedOrder = orderService.changeOrderStatus(id, status);
        String message = messageHelper.getMessage(
                "order.status.changed", locale
        );
        return ResponseEntity.ok(new ApiResponse<>(message, updatedOrder));
    }
}
