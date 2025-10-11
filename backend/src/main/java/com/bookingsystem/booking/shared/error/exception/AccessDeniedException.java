package com.bookingsystem.booking.shared.error.exception;

public class AccessDeniedException extends RuntimeException {
    
    public AccessDeniedException(String message) { super(message); }
}
