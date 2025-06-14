package com.cart.payment.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponse {
    private Long paymentId;
    private Long orderId;
    private String status;
    private Double amount;
    public PaymentResponse(Long paymentId, Long orderId, String status, Double amount) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.status = status;
        this.amount = amount;
    }
}
