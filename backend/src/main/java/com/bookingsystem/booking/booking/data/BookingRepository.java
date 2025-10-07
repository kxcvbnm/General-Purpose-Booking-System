package com.bookingsystem.booking.booking.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookingsystem.booking.booking.domain.entities.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    
}
