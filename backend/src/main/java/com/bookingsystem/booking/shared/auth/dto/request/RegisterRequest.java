package com.bookingsystem.booking.shared.auth.dto.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    
    @NotBlank
    @Email
    @Pattern(regexp = "^\\S+$", message = "Username must not contain spaces")
    @Size(max = 100)
    String email,
    
    @NotBlank
    @Pattern(regexp = "^\\S+$", message = "Username must not contain spaces")
    @Size(min = 3, max = 50)
    String username,
    
    @NotBlank
    @Pattern(regexp = "^\\S+$", message = "Username must not contain spaces")
    @Size(min = 8, max = 64)
    String password,

    @NotBlank
    @Pattern(regexp = "^\\S+$", message = "Username must not contain spaces")
    String confirmPassword
) {
    @AssertTrue(message = "Password does not match")
    public boolean isConfirmed() {
        return password != null && password.equals(confirmPassword);
    }
}
