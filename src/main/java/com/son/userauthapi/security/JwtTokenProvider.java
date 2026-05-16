package com.son.userauthapi.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}") // ĐÚNG: Đặt ngay trên biến private String
    private String jwtSecret;

    private final long JWT_EXPIRATION_MS = 86400000; // 24 hours

    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION_MS);

        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        return Jwts.builder().setSubject(username)
                .setIssuedAt(now).setExpiration(expiryDate).signWith(key, SignatureAlgorithm.HS256).compact();
    }

    //    Get username from token
    public String getUsernameFromToken(String token) {
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    //    Check if token is valid
    public boolean validateToken(String token) {
        try {
            Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
