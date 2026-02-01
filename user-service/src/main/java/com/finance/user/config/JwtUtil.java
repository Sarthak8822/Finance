package com.finance.user.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * JWT Utility Class
 *
 * JWT (JSON Web Token) - Ye ek token hai jo user authentication ke liye use hota hai
 *
 * Kaise kaam karta hai:
 * 1. User login karta hai
 * 2. Server ek JWT token generate karta hai
 * 3. User har request mein ye token bhejta hai
 * 4. Server token verify karke user ko identify karta hai
 *
 * Fayda: Session management ki zarurat nahi, stateless authentication
 */
@Component
public class JwtUtil {

    // Secret key for signing JWT (production mein ye environment variable se aani chahiye)
    @Value("${jwt.secret:mySecretKeyForJWTTokenGenerationFinanceTrackerApp12345}")
    private String jwtSecret;

    // Token expiration time (24 hours)
    @Value("${jwt.expiration:86400000}")
    private Long jwtExpirationMs;

    /**
     * Generate JWT Token
     *
     * @param username - User ka username
     * @return JWT token string
     */
    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        // Token banao with username as subject
        return Jwts.builder()
                .setSubject(username)  // Username token mein store hoga
                .setIssuedAt(now)  // Token kab issue hua
                .setExpiration(expiryDate)  // Token kab expire hoga
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)  // Sign with secret key
                .compact();
    }

    /**
     * Get Username from JWT Token
     *
     * @param token - JWT token
     * @return username
     */
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    /**
     * Validate JWT Token
     *
     * @param token - JWT token
     * @return true if valid, false otherwise
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get Signing Key from secret
     */
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }
}