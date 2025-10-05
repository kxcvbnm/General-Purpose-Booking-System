package com.bookingsystem.booking.exceptionhandlers;

public class ConflictException extends RuntimeException {
    
    public ConflictException(String message) { super(message); }
}

