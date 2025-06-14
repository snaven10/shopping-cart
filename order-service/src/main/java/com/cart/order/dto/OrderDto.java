package com.cart.order.dto;

import lombok.Data;

@Data
public class OrderDto {
    private Long productId;
    private Integer quantity;
    private String username;
}
