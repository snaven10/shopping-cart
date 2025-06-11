package com.cart.payment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @GetMapping("/public")
    public ResponseEntity<String> publicPaymentInfo() {
        return ResponseEntity.ok("💰 Public payment info");
    }

    @GetMapping("/secure")
    public ResponseEntity<String> securePaymentInfo() {
        return ResponseEntity.ok("🔐 Secure payment info (JWT validated)");
    }
}
