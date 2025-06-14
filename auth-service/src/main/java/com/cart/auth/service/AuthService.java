package com.cart.auth.service;

import com.cart.auth.entity.UserEntity;
import com.cart.auth.repository.UserRepository;
import com.cart.common.security.JwtService;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String login(String username, String password) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userId", user.getId());
        return jwtService.generateToken(extraClaims, user.getUsername());

    }    
}
