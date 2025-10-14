package com.bookingsystem.booking.user.api.dtos.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserUpdateRequest(
   
    @Size(max = 50) String username,
    @Email @Size(max = 100) String email
) {}
