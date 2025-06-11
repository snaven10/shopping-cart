package com.cart.common.security;

public interface JwtService {
    String generateToken(String username) throws Exception;
    String extractUsername(String token) throws Exception;
    boolean isTokenValid(String token);
}
