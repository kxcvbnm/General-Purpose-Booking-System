package com.bookingsystem.booking.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookingsystem.booking.shared.error.ConflictException;
import com.bookingsystem.booking.shared.error.NotFoundException;
import com.bookingsystem.booking.shared.security.PasswordHasher;
import com.bookingsystem.booking.user.api.dtos.request.UserCreateRequest;
import com.bookingsystem.booking.user.api.dtos.request.UserUpdateRequest;
import com.bookingsystem.booking.user.api.dtos.response.UserDTO;
import com.bookingsystem.booking.user.api.mappers.UserMapper;
import com.bookingsystem.booking.user.data.UserRepository;
import com.bookingsystem.booking.user.domain.entities.User;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;

    public UserService(UserRepository userRepository, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }

    @Transactional
    public UserDTO createUser(UserCreateRequest req) {
        if (userRepository.existsByEmailIgnoreCase(req.getEmail())) {
            throw new ConflictException("Email already exists"); 
        }
        User user = new User();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(passwordHasher.hash(req.getPassword()));
        user.setRole(req.getRole());

        User saved = userRepository.save(user);
        return UserMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getAllUser() {
        return UserMapper.toDtoList(userRepository.findAll());
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("User not found " + id));
        return UserMapper.toDto(user);
    }

    // Partial Update
    @Transactional
    public UserDTO updateUser(Long id, UserUpdateRequest req) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("User not found " + id));
        
        if(req.getUsername() != null) {
            user.setUsername(req.getUsername());
        }

        if(req.getEmail() != null) {
            if(userRepository.existsByEmailIgnoreCaseAndIdNot(req.getEmail(), id)) {
                throw new ConflictException("Email already exists"); 
            }
            user.setEmail(req.getEmail());
        }

        if(req.getPassword() != null) {
            user.setPassword(passwordHasher.hash(req.getPassword()));
        }

        if(req.getRole() != null) {
            user.setRole(req.getRole());
        }

        User saved = userRepository.save(user);
        return UserMapper.toDto(saved);
    }

    // Soft Delete
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("user not found: " + id);
        }
        userRepository.deleteById(id);
    }
}
