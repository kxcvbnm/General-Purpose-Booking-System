package com.bookingsystem.booking.user.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookingsystem.booking.user.domain.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);
}
