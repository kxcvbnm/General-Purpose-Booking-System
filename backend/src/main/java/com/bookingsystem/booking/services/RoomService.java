package com.bookingsystem.booking.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bookingsystem.booking.dto.requestdto.room.RoomCreateRequest;
import com.bookingsystem.booking.dto.returndto.RoomDTO;
import com.bookingsystem.booking.exceptionhandlers.NotFoundException;
import com.bookingsystem.booking.mappers.RoomMapper;
import com.bookingsystem.booking.models.Room;
import com.bookingsystem.booking.repositories.RoomRepository;

import jakarta.transaction.Transactional;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Transactional
    public RoomDTO createRoom(RoomCreateRequest req) {
        Room room = new Room();
        room.setName(req.getName());
        Room saved = roomRepository.save(room);
        return RoomMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public RoomDTO getRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Room not found" + id));
        return RoomMapper.toDto(room);
    }

    @Transactional(readOnly = true)
    public List<RoomDTO> getAllRooms() {
        return RoomMapper.toDtoList(roomRepository.findAll());
    }

    @Transactional
    public void deleteRoom(Long id) {
        if(!roomRepository.existsById(id)) {
            throw new NotFoundException("Room not found");
        }
        roomRepository.deleteById(id);
    }
}
