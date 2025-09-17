package com.bookingsystem.booking.dto;

import java.time.LocalDateTime;

public class BookingDTO {

    private final Long id;
    private final Long userId;
    private final Long roomId;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final String status;

    public BookingDTO(Long id, Long userId, Long roomId,
                      LocalDateTime startTime, LocalDateTime endTime,
                      String status) {
        this.id = id;
        this.userId = userId;
        this.roomId = roomId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getStatus() {
        return status;
    }

}
