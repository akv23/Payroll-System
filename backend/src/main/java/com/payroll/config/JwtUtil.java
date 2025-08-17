package com.payroll.config;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;


import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.payroll.model.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;



@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpirationInMs;
   
    // Method to generate a JWT token
    public String generateToken(String username, Role role) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(key)
                .compact();
    }

    // Method to extract username from JWT token
    public String extractUsername(String token) {
        try {
            token = validateTokenFormat(token); // Pre-validate token format
            return Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseSignedClaims(token) // Validate signature
                    .getPayload()
                    .getSubject();
        } catch (SignatureException e) {
            throw new IllegalArgumentException("Invalid JWT signature: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to extract username from token: " + e.getMessage(), e);
        }
    }

    // Method to extract role from JWT token
    public String extractRoles(String token) {
        try {
            token = validateTokenFormat(token);
            Claims claims = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return claims.get("role", String.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to extract roles from token: " + e.getMessage(), e);
        }
    }


    // Method to validate the JWT token
    public boolean validateToken(String token) {
        try {
            token = validateTokenFormat(token); // Pre-validate token format
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseSignedClaims(token); // Validate signature
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("Token expired: " + e.getMessage());
            throw e; // Rethrow to be caught by the filter
        } catch (SignatureException e) {
            System.out.println("Invalid signature: " + e.getMessage());
            throw new JwtException("Invalid token signature");
        } catch (Exception e) {
            System.out.println("Invalid token: " + e.getMessage());
            throw new JwtException("Invalid token");
        }
    }
    
    // Method to validate the format of the JWT token
    private String validateTokenFormat(String token) {
        if (token == null || token.trim().isEmpty()) {
            System.out.println("JWT token is missing or empty.");
            throw new IllegalArgumentException("JWT token is missing or empty.");
        }
        if (!token.contains(".") || token.split("\\.").length != 3) {
            System.out.println("Invalid JWT token format");
            throw new IllegalArgumentException("Invalid JWT token format. Must have 3 parts: header.payload.signature");
        }
        return token;
    }
    
}
