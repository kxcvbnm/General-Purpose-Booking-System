package com.bookingsystem.booking.shared.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.bookingsystem.booking.booking.data.BookingRepository;

@Component("policy")
public class Policy {
    
    private final BookingRepository bookingRepository;

    public Policy(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public boolean isAdmin(Authentication auth) {
        return auth.getAuthorities()
            .stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    public boolean canViewBooking(Long bookingId, Authentication auth) {
        if(isAdmin(auth)) return true;

        UserPrincipal user = (UserPrincipal) auth.getPrincipal();
        return bookingRepository.existsByIdAndUserId(bookingId, user.getId());
    }

    public boolean canModifyBooking(Long bookingId, Authentication auth) {
        return canViewBooking(bookingId, auth);
    }
    
}
