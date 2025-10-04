package com.bookingsystem.booking.dto.requestdto.room;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record RoomCreateRequest(
    
    @NotBlank String name,
    @Positive int capacity
) {}