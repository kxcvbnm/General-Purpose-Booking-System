package com.bookingsystem.booking.shared.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RefreshRequest (
    
    @NotBlank
    String refreshToken
) {}
