package com.cart.auth;

import com.cart.common.security.JwtService;
import com.cart.common.security.impl.JwtServiceImpl;

public class JwtTest {

    public static void main(String[] args) {
        JwtService jwtService = new JwtServiceImpl(); // ✅ implementación directa para pruebas

        try {
            // 🔐 Generar token
            String token = jwtService.generateToken("test-user");
            System.out.println("🔐 Token generado:\n" + token);

            // ✔️ Validar token
            boolean isValid = jwtService.isTokenValid(token);
            System.out.println("✔️ ¿Token válido? " + isValid);

            // 👤 Obtener el usuario
            String username = jwtService.extractUsername(token);
            System.out.println("👤 Subject extraído: " + username);

        } catch (Exception e) {
            System.err.println("❌ Error en la prueba JWT: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
