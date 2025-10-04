package com.bookingsystem.booking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookingsystem.booking.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);
}
