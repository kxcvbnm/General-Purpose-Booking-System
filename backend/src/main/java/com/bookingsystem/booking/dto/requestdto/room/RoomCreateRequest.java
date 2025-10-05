package com.bookingsystem.booking.dto.requestdto.room;

import com.bookingsystem.booking.models.enums.RoomType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RoomCreateRequest {
    
    @NotBlank
    private String name;

    @Size(max = 200)
    private String description; // optional

    @NotNull
    @Min(1)
    private Integer capacity;

    @NotNull
    private RoomType type;

    public String getName() { 
        return name; 
    }

    public void setName(String name) { 
        this.name = name; 
    }

    public String getDescription() { 
        return description; 
    }

    public void setDescription(String description) { 
        this.description = description; 
    }

    public Integer getCapacity() { 
        return capacity; 
    }

    public void setCapacity(Integer capacity) { 
        this.capacity = capacity; 
    }

    public RoomType getType() { 
        return type; 
    }
    
    public void setType(RoomType type) { 
        this.type = type; 
    }
}