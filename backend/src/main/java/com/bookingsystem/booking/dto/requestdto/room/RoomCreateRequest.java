package com.bookingsystem.booking.dto.requestdto.room;

import jakarta.validation.constraints.NotBlank;

public class RoomCreateRequest {
    
    @NotBlank
    private String name;

    public String getName() { 
        return name; 
    }
    
    public void setName(String name) { 
        this.name = name; 
    }
}