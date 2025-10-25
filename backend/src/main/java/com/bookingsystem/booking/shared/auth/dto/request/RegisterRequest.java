package com.bookingsystem.booking.shared.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    
    @NotBlank
    @Email
    @Size(max = 100)
    String email,
    
    @NotBlank
    @Size(max = 50)
    String username,
    
    @NotBlank
    @Size(min = 8, max = 64)
    String password,

    @NotBlank
    String confirmPassword
) {}
