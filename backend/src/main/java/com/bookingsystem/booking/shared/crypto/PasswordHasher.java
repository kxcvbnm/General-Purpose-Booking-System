package com.bookingsystem.booking.shared.crypto;

public interface PasswordHasher {
    String hash(String raw);
    boolean matches(String raw, String hashed);
}
