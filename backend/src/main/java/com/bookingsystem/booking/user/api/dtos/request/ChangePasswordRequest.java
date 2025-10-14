package com.bookingsystem.booking.user.api.dtos.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePasswordRequest(
    
    @NotBlank
    String currentPassword,
    
    @NotBlank
    @Size(min = 8, max = 64) 
    String newPassword,

    @NotBlank
    String confirmPassword
) {
    
    @AssertTrue(message = "New password and confirmation do not match")
    public boolean isConfirmed() {
        return newPassword != null && newPassword.equals(confirmPassword);
    }
}
