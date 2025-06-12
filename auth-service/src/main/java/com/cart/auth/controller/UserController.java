package com.cart.auth.controller;

import com.cart.auth.dto.UserDto;
import com.cart.auth.dto.UserResponseDto;
import com.cart.auth.service.UserService;
import com.cart.common.response.ApiResponse;
import com.cart.common.validation.ValidationGroups.OnCreate;
import com.cart.common.validation.ValidationGroups.OnUpdate;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@Validated(OnCreate.class) @RequestBody UserDto dto) {
        try {
            UserResponseDto created = userService.createUser(dto);
            return ResponseEntity.ok(ApiResponse.success("User created successfully", created));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ApiResponse.error("Internal error: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @Validated(OnUpdate.class) @RequestBody UserDto dto) {
        try {
            UserResponseDto updated = userService.updateUser(id, dto);
            return ResponseEntity.ok(ApiResponse.success("User updated successfully", updated));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ApiResponse.error("Internal error: " + e.getMessage()));
        }
    }
}
