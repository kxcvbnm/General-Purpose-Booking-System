package com.bookingsystem.booking.room.api.dtos.response;

import com.bookingsystem.booking.room.domain.enums.RoomType;

public record RoomDTO(
    Long id,
    String name,
    RoomType type
) {}
