package com.bookingsystem.booking.dto.returndto;

import java.time.OffsetDateTime;

public class BookingDTO {

    private final Long id;
    private final Long userId;
    private final Long roomId;
    private final OffsetDateTime startTime;
    private final OffsetDateTime endTime;
    private final String status;

    public BookingDTO(Long id, Long userId, Long roomId,
                      OffsetDateTime startTime, OffsetDateTime endTime,
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

    public OffsetDateTime getStartTime() {
        return startTime;
    }

    public OffsetDateTime getEndTime() {
        return endTime;
    }

    public String getStatus() {
        return status;
    }

}
