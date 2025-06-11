package com.cart.common.security.impl;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.*;
import java.security.spec.*;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.cart.common.security.JwtService;
import com.cart.common.security.impl.JwtServiceImpl;

@Service
public class JwtServiceImpl implements JwtService {

    private PrivateKey getPrivateKey() throws Exception {
        InputStream is = JwtServiceImpl.class.getClassLoader().getResourceAsStream("keys/private.pem");
        if (is == null)
            throw new FileNotFoundException("Private key not found in resources");
        String key = new String(is.readAllBytes())
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
        byte[] keyBytes = Decoders.BASE64.decode(key);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        return KeyFactory.getInstance("RSA").generatePrivate(spec);
    }

    private PublicKey getPublicKey() throws Exception {
        InputStream is = JwtServiceImpl.class.getClassLoader().getResourceAsStream("keys/public.pem");
        if (is == null)
            throw new FileNotFoundException("Public key not found in resources");
        String key = new String(is.readAllBytes())
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
        byte[] keyBytes = Decoders.BASE64.decode(key);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        return KeyFactory.getInstance("RSA").generatePublic(spec);
    }

    @Override
    public String generateToken(String username) throws Exception {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .signWith(getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();
    }

    @Override
    public String extractUsername(String token) throws Exception {
        return getClaims(token).getSubject();
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

    private Claims getClaims(String token) throws Exception {
        return Jwts.parserBuilder()
                .setSigningKey(getPublicKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
