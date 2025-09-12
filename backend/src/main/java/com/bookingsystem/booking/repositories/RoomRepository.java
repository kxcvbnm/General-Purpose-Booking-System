package com.bookingsystem.booking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookingsystem.booking.models.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
    
}
