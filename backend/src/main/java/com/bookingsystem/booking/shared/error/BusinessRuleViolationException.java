package com.bookingsystem.booking.shared.error;

public class BusinessRuleViolationException extends RuntimeException {
    
    public BusinessRuleViolationException(String message) { super(message); }
}
