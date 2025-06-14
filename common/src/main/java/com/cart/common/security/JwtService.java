package com.cart.common.security;

import java.util.Map;

public interface JwtService {
    String generateToken(Map<String, Object> extraClaims, String username);
    String extractUsername(String token);
    boolean isTokenValid(String token);

    Long extractUserId(String token);
}
