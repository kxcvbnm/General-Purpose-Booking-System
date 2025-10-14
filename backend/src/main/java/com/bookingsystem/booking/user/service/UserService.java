package com.bookingsystem.booking.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookingsystem.booking.shared.auth.tokens.RefreshTokenService;
import com.bookingsystem.booking.shared.crypto.PasswordHasher;
import com.bookingsystem.booking.shared.error.exception.ConflictException;
import com.bookingsystem.booking.shared.error.exception.NotFoundException;
import com.bookingsystem.booking.shared.error.exception.UnauthorizedException;
import com.bookingsystem.booking.user.api.dtos.request.UserUpdateRequest;
import com.bookingsystem.booking.user.api.dtos.response.UserDTO;
import com.bookingsystem.booking.user.api.mappers.UserMapper;
import com.bookingsystem.booking.user.data.UserRepository;
import com.bookingsystem.booking.user.domain.entities.User;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;
    private final RefreshTokenService refreshTokenService;

    public UserService(UserRepository userRepository, PasswordHasher passwordHasher, RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.refreshTokenService = refreshTokenService;
    }

    // @Transactional
    // public UserDTO createUser(UserCreateRequest req) {
    //     if (userRepository.existsByEmailIgnoreCase(req.getEmail())) {
    //         throw new ConflictException("Email already exists"); 
    //     }
    //     User user = new User();
    //     user.setUsername(req.getUsername());
    //     user.setEmail(req.getEmail());
    //     user.setPassword(passwordHasher.hash(req.getPassword()));
    //     user.setRole(req.getRole());

    //     User saved = userRepository.save(user);
    //     return UserMapper.toDto(saved);
    // }

    @Transactional(readOnly = true)
    public List<UserDTO> getAllUser() {
        return UserMapper.toDtoList(userRepository.findAll());
    }

    @Transactional(readOnly = true)
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
        
        if(req.username() != null && !req.username().isBlank()) {
            user.setUsername(req.username().trim());
        }

        if(req.email() != null && !req.email().isBlank()) {
            if(userRepository.existsByEmailIgnoreCaseAndIdNot(req.email(), id)) {
                throw new ConflictException("Email already exists"); 
            }
            user.setEmail(req.email().trim().toLowerCase());
            refreshTokenService.revokeAll(user.getId());
        }

        User saved = userRepository.save(user);
        return UserMapper.toDto(saved);
    }
 
    @Transactional
    public void changePassword(Long userId, String password, String newPassword) {
        
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found "));
        
        if(!passwordHasher.matches(password, user.getPassword())) {
            throw new UnauthorizedException("Password does not match");
        }

        if(passwordHasher.matches(newPassword, user.getPassword())) {
            throw new ConflictException("New password must be different from current");
        }

        user.setPassword(passwordHasher.hash(newPassword));
        userRepository.save(user);
        refreshTokenService.revokeAll(userId);
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("user not found: " + id);
        }
        userRepository.deleteById(id);
    }
}
