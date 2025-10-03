package com.bookingsystem.booking.dto.requestdto;

import java.time.OffsetDateTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

public class BookingRequest {
    
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Room ID is required")
    private Long roomId;

    @NotNull(message = "Start time is required")
    @FutureOrPresent(message = "Start time must be in the future")
    private OffsetDateTime startTime;

    @NotNull(message = "End time is required")
    private OffsetDateTime endTime;

    public Long getUserId() {
      return this.userId;
    }
    public void setUserId(Long userId) {
      this.userId = userId;
    }

    public Long getRoomId() {
      return this.roomId;
    }
    public void setRoomId(Long roomId) {
      this.roomId = roomId;
    }

    public OffsetDateTime getStartTime() {
      return this.startTime;
    }
    public void setStartTime(OffsetDateTime startTime) {
      this.startTime = startTime;
    }

    public OffsetDateTime getEndTime() {
      return this.endTime;
    }
    public void setEndTime(OffsetDateTime endTime) {
      this.endTime = endTime;
    }
}
