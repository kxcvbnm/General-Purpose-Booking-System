package com.bookingsystem.booking.services;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookingsystem.booking.dto.returndto.BookingDTO;
import com.bookingsystem.booking.exceptionhandlers.NotFoundException;
import com.bookingsystem.booking.mappers.BookingMapper;
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

    @Transactional
    public BookingDTO createBooking(Long userId, Long roomId, OffsetDateTime startTime, OffsetDateTime endTime) {      
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("User not found"));
        Room room = roomRepository.findById(roomId)
            .orElseThrow(() -> new NotFoundException("Room not found")); 

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setRoom(room);
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);
        booking.setStatus(BookingStatus.CONFIRMED);
        
        Booking saved = bookingRepository.save(booking);
        return BookingMapper.toDto(saved);          
    }

    @Transactional
    public BookingDTO cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Booking not found"));
        booking.setStatus(BookingStatus.CANCELLED);
        
        return BookingMapper.toDto(bookingRepository.save(booking));
    }

    @Transactional(readOnly = true)
    public List<BookingDTO> getAllBookings() {
        return BookingMapper.toDtoList(bookingRepository.findAll());
    }

    public BookingDTO getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Booking not found"));
        return BookingMapper.toDto(booking);
    }  
}
