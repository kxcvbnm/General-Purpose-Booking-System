package com.bookingsystem.booking.utils;

public interface PasswordHasher {
    String hash(String raw);
    boolean matches(String raw, String hashed);
}
