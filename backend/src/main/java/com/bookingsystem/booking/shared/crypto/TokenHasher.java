package com.bookingsystem.booking.shared.crypto;

public interface TokenHasher {
    String hash(String raw);
    boolean matches(String raw, String hashed);
}