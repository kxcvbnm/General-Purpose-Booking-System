package com.bookingsystem.booking.booking.api.dtos.request;

import java.time.OffsetDateTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

public record BookingRequest(
    @NotNull Long roomId,
    @NotNull @FutureOrPresent OffsetDateTime startTime,
    @NotNull OffsetDateTime endTime
) {}
