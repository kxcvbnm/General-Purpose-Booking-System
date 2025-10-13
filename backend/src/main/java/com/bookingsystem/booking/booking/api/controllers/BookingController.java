package com.bookingsystem.booking.booking.api.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bookingsystem.booking.booking.api.dtos.request.BookingRequest;
import com.bookingsystem.booking.booking.api.dtos.response.BookingDTO;
import com.bookingsystem.booking.booking.service.BookingService;
import com.bookingsystem.booking.shared.security.UserPrincipal;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // User create booking
    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(@Valid @RequestBody BookingRequest bookingRequest,
                                                    @AuthenticationPrincipal UserPrincipal userPrincipal) {
        BookingDTO body = bookingService.createBooking(
            userPrincipal.getId(),
            bookingRequest.roomId(),
            bookingRequest.startTime(),
            bookingRequest.endTime()
        );
  
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()     
            .path("/{id}")
            .buildAndExpand(body.id())  
            .toUri();

        return ResponseEntity.created(location).body(body); // 201 Created
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<BookingDTO>> myBookings(@AuthenticationPrincipal UserPrincipal user) {
        return  ResponseEntity.ok(bookingService.getBookingsForUser(user.getId()));
    }

    @PutMapping("/{id}/cancel")
    @PreAuthorize("@policy.canModifyBooking(#id, authentication)")
    public ResponseEntity<BookingDTO> cancelBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.cancelBooking(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/{id}")
    @PreAuthorize("@policy.canViewBooking(#id, authentication)")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    } 
}
