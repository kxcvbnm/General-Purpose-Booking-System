package com.bookingsystem.booking.room.api.dtos.request;

import com.bookingsystem.booking.room.domain.enums.RoomType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RoomCreateRequest(
    @NotBlank String name,
    @Size(max = 200) String description,
    @NotNull @Min(1) Integer capacity,
    @NotNull RoomType type
) {}