package com.bookingsystem.booking.shared.error;

public class NotFoundException extends RuntimeException {
    
    public NotFoundException(String message) { super(message); }
}
