package com.bookingsystem.booking.room.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookingsystem.booking.room.domain.entities.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
    boolean existsByNameIgnoreCase(String name);
}
