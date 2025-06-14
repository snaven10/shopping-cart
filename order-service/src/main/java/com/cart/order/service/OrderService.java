package com.cart.order.service;

import com.cart.common.response.ApiResponse;
import com.cart.order.client.ProductClient;
import com.cart.order.dto.OrderDto;
import com.cart.order.dto.OrderRequest;
import com.cart.order.entity.Order;
import com.cart.order.repository.OrderRepository;
import com.cart.order.dto.ProductDto;
import com.cart.common.security.JwtService; // ✅ Usa tu JWT service

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final JwtService jwtService;
    private final HttpServletRequest request;

    @Transactional
    public ApiResponse<?> createOrder(OrderRequest requestDto) {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("No token found in request");
        }
        String token = authHeader.substring(7);

        Long userId = jwtService.extractUserId(token);

        ProductDto product  = productClient.getProductById(requestDto.getProductId());

        Order order = new Order();
        order.setUserId(userId);
        order.setProductId(product.getId());
        order.setQuantity(requestDto.getQuantity());
        order = orderRepository.save(order);
        return ApiResponse.success("Order created successfully", order);
    }

    public OrderDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setProductId(order.getProductId());
        dto.setQuantity(order.getQuantity());
        dto.setUserId(order.getUserId());
        dto.setCreatedAt(order.getCreatedAt());

        return dto;
    }

    public ApiResponse<?> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return ApiResponse.success("Orders fetched", orders);
    }
}
