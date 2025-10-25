package com.bookingsystem.booking.user.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookingsystem.booking.user.domain.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);
    boolean existsByUsernameIgnoreCase(String username);
    boolean existsByUsernameIgnoreCaseAndIdNot(String username, Long id);
    Optional<User> findByEmailIgnoreCase(String email);
}
