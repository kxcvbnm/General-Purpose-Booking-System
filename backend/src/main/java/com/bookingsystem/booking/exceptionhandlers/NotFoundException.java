package com.bookingsystem.booking.exceptionhandlers;

public class NotFoundException extends RuntimeException {
    
    public NotFoundException(String message) { super(message); }
}
