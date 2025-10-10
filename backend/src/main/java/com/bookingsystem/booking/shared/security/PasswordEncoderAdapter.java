package com.bookingsystem.booking.shared.security;

import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderAdapter implements PasswordEncoder {

    private final PasswordHasher hasher;
    
    public PasswordEncoderAdapter(PasswordHasher hasher) {
        this.hasher = hasher;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return hasher.hash(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return hasher.matches(rawPassword.toString(), encodedPassword);
    }
}
