package com.bookingsystem.booking.shared.error.exception;

public class TokenExpiredException extends RuntimeException {

    public TokenExpiredException(String message) { super(message); }
}
