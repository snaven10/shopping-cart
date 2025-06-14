package com.cart.payment.client;

import com.cart.payment.dto.OrderDto;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    name = "product-service",
    url = "${ORDER_SERVICE_URL}"
)
public interface OrderClient {
    @GetMapping("/api/orders/internal/{orderId}")
    OrderDto getOrderById(@PathVariable("orderId") Long orderId);
}
