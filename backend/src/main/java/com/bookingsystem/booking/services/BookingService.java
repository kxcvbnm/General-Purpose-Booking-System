package com.bookingsystem.booking.services;

import org.springframework.stereotype.Service;

import com.bookingsystem.booking.repositories.BookingRepository;
import com.bookingsystem.booking.repositories.RoomRepository;
import com.bookingsystem.booking.repositories.UserRepository;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    public BookingService(BookingRepository bookingRepository, UserRepository userRepository, RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    
    
}
