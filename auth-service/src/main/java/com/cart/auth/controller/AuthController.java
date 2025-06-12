package com.cart.auth.controller;

import com.cart.auth.dto.LoginRequest;
import com.cart.auth.dto.LoginResponse;
import com.cart.auth.service.AuthService;
import com.cart.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String token = authService.login(request.getUsername(), request.getPassword());
        LoginResponse loginResponse = new LoginResponse(token, request.getUsername());
        return ResponseEntity.ok(ApiResponse.success("Login successful", loginResponse));
    }

}
