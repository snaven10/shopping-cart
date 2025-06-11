package com.cart.auth.controller;

import com.cart.auth.security.JwtUtils;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/token")
    public ResponseEntity<?> generateToken(@RequestParam("username") String username) {
        try {
            String token = JwtUtils.generateToken(username);
            return ResponseEntity.ok().body("Bearer " + token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error generating token: " + e.getMessage());
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestParam("token") String token) {
        System.out.println(">>> Validating token: " + token); // debug
        try {
            boolean isValid = JwtUtils.isTokenValid(token);
            String username = isValid ? JwtUtils.extractUsername(token) : "Invalid token";
            return ResponseEntity.ok().body(Map.of(
                    "valid", isValid,
                    "username", username));
        } catch (Exception e) {
            e.printStackTrace(); // debug
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Token validation failed", "details", e.getMessage()));
        }
    }
}
