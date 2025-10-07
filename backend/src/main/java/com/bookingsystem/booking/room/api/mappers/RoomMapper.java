package com.bookingsystem.booking.room.api.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.bookingsystem.booking.room.api.dtos.response.RoomDTO;
import com.bookingsystem.booking.room.domain.entities.Room;

public class RoomMapper {

    public static RoomDTO toDto(Room room) {
        return new RoomDTO(
            room.getId(),
            room.getName(),
            room.getType()
        );
    }

    public static List<RoomDTO> toDtoList(List<Room> rooms) {
        return rooms.stream()
                    .map(RoomMapper::toDto)
                    .collect(Collectors.toList());
    }
}
