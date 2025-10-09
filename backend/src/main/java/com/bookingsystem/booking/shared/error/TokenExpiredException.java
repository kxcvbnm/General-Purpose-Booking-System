package com.bookingsystem.booking.shared.error;

public class TokenExpiredException extends RuntimeException {

    public TokenExpiredException(String message) { super(message); }
}
