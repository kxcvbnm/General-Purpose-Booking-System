package com.bookingsystem.booking.shared.security;

public interface PasswordHasher {
    String hash(String raw);
    boolean matches(String raw, String hashed);
}
