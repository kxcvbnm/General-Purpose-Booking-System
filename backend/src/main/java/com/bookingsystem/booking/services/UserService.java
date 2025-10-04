package com.bookingsystem.booking.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookingsystem.booking.dto.requestdto.user.UserCreateRequest;
import com.bookingsystem.booking.dto.requestdto.user.UserUpdateRequest;
import com.bookingsystem.booking.models.User;
import com.bookingsystem.booking.repositories.UserRepository;
import com.bookingsystem.booking.utils.PasswordHasher;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;

    public UserService(UserRepository userRepository, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }

    @Transactional
    public User createUser(UserCreateRequest req) {
        if (userRepository.existsByEmailIgnoreCase(req.getEmail())) {
            throw new IllegalStateException("Email already exists"); 
        }
        User user = new User();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(passwordHasher.hash(req.getPassword()));
        user.setRole(req.getRole());
        return userRepository.save(user);
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                             .orElseThrow(() -> new IllegalArgumentException("User not found " + id));
    }

    // Partial Update
    @Transactional
    public User updateUser(Long id, UserUpdateRequest req) {
        User user = userRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("User not found " + id));
        
        if(req.getUsername() != null) {
            user.setUsername(req.getUsername());
        }

        if(req.getEmail() != null) {
            if(userRepository.existsByEmailIgnoreCaseAndIdNot(req.getEmail(), id)) {
                throw new IllegalStateException("Email already exists"); 
            }
            user.setEmail(req.getEmail());
        }

        if(req.getPassword() != null) {
            user.setPassword(passwordHasher.hash(req.getPassword()));
        }

        if(req.getRole() != null) {
            user.setRole(req.getRole());
        }
        return userRepository.save(user);
    }

    // Soft Delete
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("user not found: " + id);
        }
        userRepository.deleteById(id);
    }
}
