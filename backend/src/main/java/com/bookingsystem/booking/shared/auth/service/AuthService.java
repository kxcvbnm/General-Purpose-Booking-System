package com.bookingsystem.booking.shared.auth.service;

import java.time.OffsetDateTime;
import static java.time.ZoneOffset.UTC;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookingsystem.booking.shared.auth.dto.request.LoginRequest;
import com.bookingsystem.booking.shared.auth.dto.request.RefreshRequest;
import com.bookingsystem.booking.shared.auth.dto.request.RegisterRequest;
import com.bookingsystem.booking.shared.auth.dto.response.TokenResponse;
import com.bookingsystem.booking.shared.auth.tokens.RefreshToken;
import com.bookingsystem.booking.shared.auth.tokens.RefreshTokenService;
import com.bookingsystem.booking.shared.crypto.PasswordHasher;
import com.bookingsystem.booking.shared.error.exception.ConflictException;
import com.bookingsystem.booking.shared.error.exception.InvalidTokenException;
import com.bookingsystem.booking.shared.security.JwtService;
import com.bookingsystem.booking.shared.security.UserPrincipal;
import com.bookingsystem.booking.user.api.dtos.response.UserDTO;
import com.bookingsystem.booking.user.api.mappers.UserMapper;
import com.bookingsystem.booking.user.data.UserRepository;
import com.bookingsystem.booking.user.domain.entities.User;
import com.bookingsystem.booking.user.domain.enums.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

@Service
public class AuthService {
    
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final PasswordHasher passwordHasher;

    public AuthService(AuthenticationManager authenticationManager, 
                       JwtService jwtService, 
                       UserRepository userRepository, 
                       RefreshTokenService refreshTokenService,
                       PasswordHasher passwordHasher) {
        
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.refreshTokenService = refreshTokenService;
        this.passwordHasher = passwordHasher;
    }

    @Transactional
    public UserDTO register(RegisterRequest req) {

        if (userRepository.existsByEmailIgnoreCase(req.email())) {
        throw new ConflictException("Email already exists");
        }

        User user = new User();
        user.setEmail(req.email().trim().toLowerCase());
        user.setUsername(req.username().trim());
        user.setPassword(passwordHasher.hash(req.password()));
        user.setRole(Role.USER);
        
        User saved = userRepository.save(user);
        return UserMapper.toDto(saved);
    }


    @Transactional
    public TokenResponse login(LoginRequest req) {
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(req.email(), req.password());
        Authentication authentication = authenticationManager.authenticate(authRequest);

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        // Generate tokens
        String accessToken  = jwtService.generateAccessToken(principal);
        String refreshToken = jwtService.generateRefreshToken(principal);

        // Store refresh token
        Claims refreshClaims = jwtService.parse(refreshToken).getBody();
        OffsetDateTime refreshExpiresAt = OffsetDateTime.ofInstant(
            refreshClaims.getExpiration().toInstant(), UTC);

        // Load User entity and store hashed refresh
        User user = userRepository.findById(principal.getId()).orElseThrow();

        refreshTokenService.store(user, refreshToken, refreshExpiresAt);

        // Return DTO
        return new TokenResponse(accessToken, refreshToken);
    }

    @Transactional
    public TokenResponse refresh(RefreshRequest req) {
        Jws<Claims> jws = jwtService.parse(req.refreshToken());

        if (!"refresh".equals(jws.getBody().get("type", String.class))) {
            throw new InvalidTokenException("Invalid refresh token");
        }

        RefreshToken valid = refreshTokenService.findValid(req.refreshToken())
                .orElseThrow(() -> new InvalidTokenException("Invalid refresh token"));

        refreshTokenService.rotate(valid);

        Long userId = Long.valueOf(jws.getBody().getSubject());
        User user = userRepository.findById(userId).orElseThrow();

        UserPrincipal principal = new UserPrincipal(user);

        String newAccessToken  = jwtService.generateAccessToken(principal);
        String newRefreshToken = jwtService.generateRefreshToken(principal);

        Claims newRefreshClaims = jwtService.parse(newRefreshToken).getBody();
        OffsetDateTime newRefreshExpiresAt = OffsetDateTime.ofInstant(
            newRefreshClaims.getExpiration().toInstant(), UTC);

        refreshTokenService.store(user, newRefreshToken, newRefreshExpiresAt);

        return new TokenResponse(newAccessToken, newRefreshToken);    
    }

    public void logout(Long userId) {
        refreshTokenService.revokeAll(userId);
    }

}
