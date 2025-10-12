package com.bookingsystem.booking.user.api.dtos.response;

import com.bookingsystem.booking.user.domain.enums.Role;

public record UserDTO(
    Long id, 
    String username, 
    String email, 
    Role role
) {}
