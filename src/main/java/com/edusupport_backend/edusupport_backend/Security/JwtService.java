package com.edusupport_backend.edusupport_backend.Security;

import com.edusupport_backend.edusupport_backend.Entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration;


    // =========================================================
    // Generate JWT Token
    // =========================================================

    public String generateToken(User user) {

        return Jwts.builder()

                // User email
                .subject(user.getEmail())

                // User ID
                .claim("userId", user.getId())

                // User role
                .claim(
                        "role",
                        user.getRole().getName()
                )

                // Token creation time
                .issuedAt(new Date())

                // Token expiration time
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + jwtExpiration
                        )
                )

                // Sign JWT
                .signWith(getSigningKey())

                // Generate token
                .compact();
    }


    // =========================================================
    // Extract Username / Email
    // =========================================================

    public String extractUsername(String token) {

        return extractClaim(
                token,
                Claims::getSubject
        );
    }


    // =========================================================
    // Extract User ID
    // =========================================================

    public Long extractUserId(String token) {

        return extractClaim(
                token,
                claims ->
                        claims.get(
                                "userId",
                                Long.class
                        )
        );
    }


    // =========================================================
    // Extract Role
    // =========================================================

    public String extractRole(String token) {

        return extractClaim(
                token,
                claims ->
                        claims.get(
                                "role",
                                String.class
                        )
        );
    }


    // =========================================================
    // Validate JWT Token
    // =========================================================

    public boolean isTokenValid(
            String token,
            String username) {

        String extractedUsername =
                extractUsername(token);

        return extractedUsername.equals(username)
                && !isTokenExpired(token);
    }


    // =========================================================
    // Check Token Expiration
    // =========================================================

    private boolean isTokenExpired(String token) {

        return extractExpiration(token)
                .before(new Date());
    }


    // =========================================================
    // Extract Expiration
    // =========================================================

    private Date extractExpiration(String token) {

        return extractClaim(
                token,
                Claims::getExpiration
        );
    }


    // =========================================================
    // Generic Claim Extraction
    // =========================================================

    private <T> T extractClaim(
            String token,
            Function<Claims, T> resolver) {

        Claims claims =
                Jwts.parser()

                        .verifyWith(
                                getSigningKey()
                        )

                        .build()

                        .parseSignedClaims(token)

                        .getPayload();

        return resolver.apply(claims);
    }


    // =========================================================
    // Create JWT Signing Key
    // =========================================================

    private SecretKey getSigningKey() {

        byte[] keyBytes =
                Decoders.BASE64.decode(
                        secretKey
                );

        return Keys.hmacShaKeyFor(
                keyBytes
        );
    }
}