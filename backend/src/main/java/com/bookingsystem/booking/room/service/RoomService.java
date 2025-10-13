package com.bookingsystem.booking.room.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookingsystem.booking.room.api.dtos.request.RoomCreateRequest;
import com.bookingsystem.booking.room.api.dtos.response.RoomDTO;
import com.bookingsystem.booking.room.api.mappers.RoomMapper;
import com.bookingsystem.booking.room.data.RoomRepository;
import com.bookingsystem.booking.room.domain.entities.Room;
import com.bookingsystem.booking.shared.error.exception.ConflictException;
import com.bookingsystem.booking.shared.error.exception.NotFoundException;


@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Transactional
    public RoomDTO createRoom(RoomCreateRequest req) {
        
        if (roomRepository.existsByNameIgnoreCase(req.name())) {
            throw new ConflictException("Room name already exists");
        }
       
        Room room = new Room();
        room.setName(req.name());
        room.setDescription(req.description());
        room.setCapacity(req.capacity());
        room.setType(req.type());
        
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
