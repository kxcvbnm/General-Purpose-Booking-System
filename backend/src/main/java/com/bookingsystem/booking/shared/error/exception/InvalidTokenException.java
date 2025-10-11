package com.bookingsystem.booking.shared.error;

public class InvalidTokenException extends RuntimeException {
   
    public InvalidTokenException(String message) { super(message); }
}
