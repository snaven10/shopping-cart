package com.cart.auth;

import com.cart.auth.security.JwtUtils;

public class JwtTest {

    public static void main(String[] args) {
        try {
            // 🔐 Generar token
            String token = JwtUtils.generateToken("test-user");
            System.out.println("🔐 Token generado:\n" + token);

            // ✅ Verificar si es válido
            boolean isValid = JwtUtils.isTokenValid(token);
            System.out.println("✔️ ¿Token válido? " + isValid);

            // 👤 Obtener el nombre de usuario (subject)
            String username = JwtUtils.extractUsername(token);
            System.out.println("👤 Usuario extraído: " + username);

        } catch (Exception e) {
            System.err.println("❌ Error ejecutando JwtTest:");
            e.printStackTrace();
        }
    }
}
