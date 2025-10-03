package com.bookingsystem.booking.services;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bookingsystem.booking.models.Booking;
import com.bookingsystem.booking.models.Room;
import com.bookingsystem.booking.models.User;
import com.bookingsystem.booking.models.enums.BookingStatus;
import com.bookingsystem.booking.repositories.BookingRepository;
import com.bookingsystem.booking.repositories.RoomRepository;
import com.bookingsystem.booking.repositories.UserRepository;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    public BookingService(BookingRepository bookingRepository,
                         UserRepository userRepository,
                         RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    public Booking createBooking(Long userId, Long roomId, OffsetDateTime startTime, OffsetDateTime endTime) {      
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Room room = roomRepository.findById(roomId)
            .orElseThrow(() -> new IllegalArgumentException("Room not found")); 

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setRoom(room);
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);
        booking.setStatus(BookingStatus.CONFIRMED);
        
        return bookingRepository.save(booking);          
    }

    public Booking cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        booking.setStatus(BookingStatus.CANCELLED);
        
        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
    }
    
}
