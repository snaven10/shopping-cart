package com.cart.order.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class OrderDto {
    private Long id;
    private Long productId;
    private Integer quantity;
    private Long userId; 
    private LocalDateTime createdAt;
}
