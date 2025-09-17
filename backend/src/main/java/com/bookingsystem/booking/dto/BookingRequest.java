package com.bookingsystem.booking.dto;

import java.time.LocalDateTime;

public class BookingRequest {
    
    private Long userId;
    private Long roomId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

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

    public LocalDateTime getStartTime() {
      return this.startTime;
    }
    public void setStartTime(LocalDateTime startTime) {
      this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
      return this.endTime;
    }
    public void setEndTime(LocalDateTime endTime) {
      this.endTime = endTime;
    }
}
