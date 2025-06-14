package com.cart.order.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponse {

    private Long id;
    private Long productId;
    private Integer quantity;
    private Long userId;
    private String createdAt;
}
