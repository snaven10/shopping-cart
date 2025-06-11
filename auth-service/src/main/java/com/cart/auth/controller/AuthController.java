package com.cart.auth.controller;

import com.cart.common.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping("/token")
    public ResponseEntity<?> generateToken(@RequestParam("username") String username) {
        try {
            String token = jwtService.generateToken(username);
            return ResponseEntity.ok().body("Bearer " + token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error generating token: " + e.getMessage());
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestParam("token") String token) {
        System.out.println(">>> Validating token: " + token);
        try {
            boolean isValid = jwtService.isTokenValid(token);
            String username = isValid ? jwtService.extractUsername(token) : "Invalid token";
            return ResponseEntity.ok().body(Map.of(
                    "valid", isValid,
                    "username", username));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Token validation failed", "details", e.getMessage()));
        }
    }
}
