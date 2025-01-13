package com.sivaprakash.auth_service.util;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(String username,Long userId,String customerId) {
        return Jwts.builder().setSubject(username)
        		.claim("userid", userId)
        		.claim("customerId", customerId)
        		.setIssuedAt(new Date())
        		.setExpiration(new Date(System.currentTimeMillis() + expiration))
        		.signWith(getSignKey(), SignatureAlgorithm.HS384).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}