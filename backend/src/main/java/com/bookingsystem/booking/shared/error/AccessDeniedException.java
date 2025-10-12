package com.bookingsystem.booking.shared.error;

public class AccessDeniedException extends RuntimeException {
    
    public AccessDeniedException(String message) { super(message); }
}
