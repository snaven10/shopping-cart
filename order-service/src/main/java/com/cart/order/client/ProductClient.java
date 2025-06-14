package com.cart.order.client;

import com.cart.order.dto.ProductDto;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    name = "product-service",
    url = "${PRODUCT_SERVICE_URL}"  // asegúrate que coincida con tu puerto
)
public interface ProductClient {
    @GetMapping("/api/products/internal/{productId}")
    ProductDto getProductById(@PathVariable("productId") Long productId);
}
