package com.bookingsystem.booking.shared.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtService {
    private final JwtProperties props;
    private final Key key;

    public JwtService(JwtProperties props) {
        this.props = props;
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
            .setExpiration(Date.from(now.plus(props.accessTokenTt1Min(), ChronoUnit.MINUTES)))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    public String generateRefreshToken(UserPrincipal principal) {
        Instant now = Instant.now();
        return Jwts.builder()
            .setSubject(principal.getId().toString())
            .setIssuer(props.issuer())
            .setAudience("refresh")
            .claim("type","refresh")
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from(now.plus(props.refreshTokenTt1Days(), ChronoUnit.DAYS)))
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
        String type = body.get("type", String.class);
        return !"refresh".equals(type);
    }

}
