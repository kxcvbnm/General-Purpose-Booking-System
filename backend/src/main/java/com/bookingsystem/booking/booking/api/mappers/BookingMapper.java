package com.bookingsystem.booking.booking.api.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.bookingsystem.booking.booking.api.dtos.response.BookingDTO;
import com.bookingsystem.booking.booking.domain.entities.Booking;

public class BookingMapper {
    
    // Convert a single Booking entity to BookingDTO
    public static BookingDTO toDto(Booking booking) {
        return new BookingDTO(
            booking.getId(),
            booking.getUser().getId(),
            booking.getRoom().getId(),
            booking.getStartTime(),
            booking.getEndTime(),
            booking.getStatus()
        );
    }

    // Convert a list of Bookings to a list of BookingDTOs
    public static List<BookingDTO> toDtoList(List<Booking> bookings) {
        return bookings.stream()
                       .map(BookingMapper::toDto)
                       .collect(Collectors.toList());
    }

}
