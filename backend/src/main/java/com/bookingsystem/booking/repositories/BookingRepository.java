package com.bookingsystem.booking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookingsystem.booking.models.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    
}
