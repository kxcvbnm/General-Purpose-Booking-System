package com.bookingsystem.booking.shared.auth.dto;

public record TokenResponse (
    String accessToken,
    String refreshToken
) {}
