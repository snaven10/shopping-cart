package com.cart.payment.service;

import com.cart.payment.client.OrderClient;
import com.cart.payment.dto.OrderDto;
import com.cart.payment.dto.PaymentRequest;
import com.cart.payment.dto.PaymentResponse;
import com.cart.payment.entity.Payment;
import com.cart.payment.repository.PaymentRepository;
import com.cart.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderClient orderClient; // ⬅️ Nuevo

    public PaymentResponse processPayment(PaymentRequest request) {

        OrderDto orderResponse = orderClient.getOrderById(request.getOrderId());

        if (orderResponse == null) {
            throw new RuntimeException("Order not found");
        }

        String status = "SUCCESS";

        Payment payment = Payment.builder()
                .orderId(request.getOrderId())
                .amount(request.getAmount())
                .status(status)
                .build();

        payment = paymentRepository.save(payment);

        return PaymentResponse.builder()
                .paymentId(payment.getId())
                .orderId(payment.getOrderId())
                .status(payment.getStatus())
                .amount(payment.getAmount())
                .build();
    }

    public ApiResponse<?> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        return ApiResponse.success("Orders fetched", payments);
    }

    public PaymentResponse getOrderById(Long id) {
        Payment payments = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        PaymentResponse dto = new PaymentResponse(id, payments.getOrderId(), payments.getStatus(), payments.getAmount());
        return dto;
    }

}
