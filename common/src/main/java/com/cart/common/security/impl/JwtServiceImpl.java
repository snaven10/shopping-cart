package com.cart.common.security.impl;

import com.cart.common.security.JwtProcessingException;
import com.cart.common.security.JwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.*;
import java.util.Date;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {

    private PrivateKey getPrivateKey() {
        try {
            InputStream is = JwtServiceImpl.class.getClassLoader().getResourceAsStream("keys/private.pem");
            if (is == null) throw new IllegalArgumentException("Private key not found");
            String key = new String(is.readAllBytes())
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");
            byte[] keyBytes = Decoders.BASE64.decode(key);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            return KeyFactory.getInstance("RSA").generatePrivate(spec);
        } catch (Exception e) {
            throw new JwtProcessingException("Error loading private key", e);
        }
    }

    private PublicKey getPublicKey() {
        try {
            InputStream is = JwtServiceImpl.class.getClassLoader().getResourceAsStream("keys/public.pem");
            if (is == null) throw new IllegalArgumentException("Public key not found");
            String key = new String(is.readAllBytes())
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");
            byte[] keyBytes = Decoders.BASE64.decode(key);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            return KeyFactory.getInstance("RSA").generatePublic(spec);
        } catch (Exception e) {
            throw new JwtProcessingException("Error loading public key", e);
        }
    }

    @Override
    public String generateToken(Map<String, Object> extraClaims, String username) {
        try {
            return Jwts.builder()
                    .setClaims(extraClaims)
                    .setSubject(username)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                    .signWith(getPrivateKey(), SignatureAlgorithm.RS256)
                    .compact();
        } catch (Exception e) {
            throw new JwtProcessingException("Error generating token with extra claims", e);
        }
    }

    @Override
    public Long extractUserId(String token) {
        try {
            Object userId = getClaims(token).get("userId");
            if (userId instanceof Integer) {
                return ((Integer) userId).longValue();
            } else if (userId instanceof Long) {
                return (Long) userId;
            } else {
                throw new JwtProcessingException("Invalid userId claim type: " + userId,null);
            }
        } catch (Exception e) {
            throw new JwtProcessingException("Error extracting userId", e);
        }
    }

    @Override
    public String extractUsername(String token) {
        try {
            return getClaims(token).getSubject();
        } catch (Exception e) {
            throw new JwtProcessingException("Error extracting username", e);
        }
    }

    @Override
    public boolean isTokenValid(String token) {
        try {
            Claims claims = getClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getPublicKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new JwtProcessingException("Error parsing token claims", e);
        }
    }
}
