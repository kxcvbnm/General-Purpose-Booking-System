package com.bookingsystem.booking.booking.service;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookingsystem.booking.booking.api.dtos.response.BookingDTO;
import com.bookingsystem.booking.booking.api.mappers.BookingMapper;
import com.bookingsystem.booking.booking.data.BookingRepository;
import com.bookingsystem.booking.booking.domain.entities.Booking;
import com.bookingsystem.booking.booking.domain.enums.BookingStatus;
import com.bookingsystem.booking.room.data.RoomRepository;
import com.bookingsystem.booking.room.domain.entities.Room;
import com.bookingsystem.booking.shared.error.exception.BusinessRuleViolationException;
import com.bookingsystem.booking.shared.error.exception.NotFoundException;
import com.bookingsystem.booking.user.data.UserRepository;
import com.bookingsystem.booking.user.domain.entities.User;

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
        
        if(userId == null || roomId == null || startTime == null || endTime == null) {
            throw new BusinessRuleViolationException("Missing required fields.");
        }

        if(!startTime.isBefore(endTime)) {
            throw new BusinessRuleViolationException("Start time must be before end time.");
        }

        if(startTime.isBefore(OffsetDateTime.now())) {
            throw new BusinessRuleViolationException("Start time must be in the future.");
        }

        long minutes = Duration.between(startTime, endTime).toMinutes();
        
        if(minutes < 60) {
            throw new BusinessRuleViolationException("Minimum booking duration is 60 minutes.");
        }

        if(minutes > 120) {
            throw new BusinessRuleViolationException("Maximum booking duration is 120 minutes.");
        }

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("User not found"));
        
        Room room = roomRepository.findByIdForUpdate(roomId)
            .orElseThrow(() -> new NotFoundException("Room not found")); 

        //check booking time in the same room shouldn't collapse
        if(bookingRepository.existsOverlapForUser(userId, startTime, endTime, BookingStatus.CONFIRMED)) {
            throw new BusinessRuleViolationException("User already has a booking at this time.");
        }

        if(bookingRepository.existsOverlapForRoom(roomId, startTime, endTime, BookingStatus.CONFIRMED)) {
            throw new BusinessRuleViolationException("Room already has a booking at this time.");
        }
        
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setRoom(room);
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);
        booking.setStatus(BookingStatus.CONFIRMED);
        
        Booking saved = bookingRepository.save(booking);
        return BookingMapper.toDto(saved);          
    }

    @Transactional(readOnly = true)
    public List<BookingDTO> getBookingsForUser(Long userId) {
        return BookingMapper.toDtoList(bookingRepository.findByUserIdOrderByStartTimeDesc(userId));
    }

    @Transactional
    public BookingDTO cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Booking not found"));

        if(booking.getStatus() == BookingStatus.CANCELLED) {
            return BookingMapper.toDto(booking);
        }

        booking.setStatus(BookingStatus.CANCELLED);
        return BookingMapper.toDto(bookingRepository.save(booking));
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    public List<BookingDTO> getAllBookings() {
        return BookingMapper.toDtoList(bookingRepository.findAll());
    }

    @Transactional(readOnly = true)
    public BookingDTO getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Booking not found"));
        return BookingMapper.toDto(booking);
    }  
}
