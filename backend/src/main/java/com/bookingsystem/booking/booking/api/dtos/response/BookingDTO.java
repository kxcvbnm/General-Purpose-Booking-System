package com.bookingsystem.booking.booking.api.dtos.response;

import java.time.OffsetDateTime;

import com.bookingsystem.booking.booking.domain.enums.BookingStatus;

public record BookingDTO(
    Long id,
    Long userId,
    Long roomId,
    OffsetDateTime startTime,
    OffsetDateTime endTime,
    BookingStatus status
) {}
