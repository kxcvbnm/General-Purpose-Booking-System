package com.bookingsystem.booking.room.api.dtos.response;

import com.bookingsystem.booking.room.domain.enums.RoomType;

public class RoomDTO {
   
    private final Long id;
    private final String name;
    private final RoomType roomType;

    public RoomDTO(Long id, String name, RoomType roomType) {
        this.id = id;
        this.name = name;
        this.roomType = roomType;
    }

    public Long getId() {
         return id; 
    }
    
    public String getName() { 
        return name; 
    }

    public RoomType getType() { 
        return roomType; 
    }
}
