package com.bookingsystem.booking.shared.auth.dto.response;

public record TokenResponse (
    String accessToken,
    String refreshToken
) {}
