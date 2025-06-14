package com.cart.order.dto;

import lombok.Data;

@Data
public class ProductDto {
    private Long id;
    private String title;
    private String description;
    private String category;
    private String image;
    private double price;
}
