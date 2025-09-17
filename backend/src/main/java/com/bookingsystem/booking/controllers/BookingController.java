package com.bookingsystem.booking.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookingsystem.booking.dto.BookingDTO;
import com.bookingsystem.booking.dto.BookingRequest;
import com.bookingsystem.booking.mappers.BookingMapper;
import com.bookingsystem.booking.models.Booking;
import com.bookingsystem.booking.services.BookingService;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(@RequestBody BookingRequest bookingRequest) {
        Booking booking = bookingService.createBooking(
            bookingRequest.getUserId(),
            bookingRequest.getRoomId(),
            bookingRequest.getStartTime(),
            bookingRequest.getEndTime()
        );
        
        return ResponseEntity.status(201).body(BookingMapper.toDto(booking)); // 201 Created
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<BookingDTO> cancelBooking(@PathVariable Long id) {
        Booking cancelled = bookingService.cancelBooking(id);
        return ResponseEntity.ok(BookingMapper.toDto(cancelled));
    }

    @GetMapping
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(BookingMapper.toDtoList(bookings));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long id) {
        Booking booking = bookingService.getBookingById(id);
        return ResponseEntity.ok(BookingMapper.toDto(booking));
    }
}
