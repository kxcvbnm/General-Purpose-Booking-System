package com.bookingsystem.booking.shared.auth.service;

import java.time.OffsetDateTime;
import static java.time.ZoneOffset.UTC;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.bookingsystem.booking.shared.auth.dto.request.LoginRequest;
import com.bookingsystem.booking.shared.auth.dto.request.RefreshRequest;
import com.bookingsystem.booking.shared.auth.dto.response.TokenResponse;
import com.bookingsystem.booking.shared.auth.tokens.RefreshToken;
import com.bookingsystem.booking.shared.auth.tokens.RefreshTokenService;
import com.bookingsystem.booking.shared.error.exception.InvalidTokenException;
import com.bookingsystem.booking.shared.security.JwtService;
import com.bookingsystem.booking.shared.security.UserPrincipal;
import com.bookingsystem.booking.user.data.UserRepository;
import com.bookingsystem.booking.user.domain.entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

@Service
public class AuthService {
    
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService, UserRepository userRepository, RefreshTokenService refreshTokenService) {
        
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.refreshTokenService = refreshTokenService;
    }

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
