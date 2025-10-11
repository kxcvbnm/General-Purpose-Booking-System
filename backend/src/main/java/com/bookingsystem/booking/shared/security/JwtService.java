package com.bookingsystem.booking.shared.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    private final JwtProperties props;
    private final Key key;

    public JwtService(JwtProperties props) {
        this.props = props;

        String secret = props.secret();
        if (secret == null || secret.isBlank()) {
            throw new IllegalStateException("JWT secret is missing. Set security.jwt.secret or JWT_SECRET env var.");
        }
        if (secret.getBytes(StandardCharsets.UTF_8).length < 32) { // ≥ 256 bits for HS256
            throw new IllegalStateException("JWT secret too short. Provide ≥32 bytes (e.g., 64+ hex chars).");
        }

        this.key = Keys.hmacShaKeyFor(props.secret().getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(UserPrincipal principal) {
        Instant now = Instant.now();
        return Jwts.builder()
            .setSubject(principal.getId().toString())
            .setIssuer(props.issuer())
            .setAudience(props.audience())
            .claim("email", principal.getUsername())
            .claim("role", principal.getRole().name())
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from(now.plus(props.accessTokenTtlMin(), ChronoUnit.MINUTES)))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    public String generateRefreshToken(UserPrincipal principal) {
        Instant now = Instant.now();
        return Jwts.builder()
            .setId(UUID.randomUUID().toString())
            .setSubject(principal.getId().toString())
            .setIssuer(props.issuer())
            .setAudience("refresh")
            .claim("type","refresh")
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from(now.plus(props.refreshTokenTtlDays(), ChronoUnit.DAYS)))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();    
    }

    public Jws<Claims> parse(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token);
    }

    public boolean isAccessToken(String token) {
        Claims body = parse(token).getBody();
        return !"refresh".equals(body.get("type", String.class));
    }

}
