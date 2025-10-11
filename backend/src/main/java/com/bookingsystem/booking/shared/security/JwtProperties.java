package com.bookingsystem.booking.shared.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security.jwt")
public record JwtProperties(
    String secret,
    String issuer,
    String audience,
    int accessTokenTtlMin,
    int refreshTokenTtlDays
) {}
