package com.cart.product.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @GetMapping("/public")
    public ResponseEntity<String> getPublicEndpoint() {
        return ResponseEntity.ok("🌐 Public product info.");
    }

    @GetMapping("/secure")
    public ResponseEntity<String> getSecureEndpoint() {
        return ResponseEntity.ok("🔐 Secure product info (JWT validated).");
    }
}
