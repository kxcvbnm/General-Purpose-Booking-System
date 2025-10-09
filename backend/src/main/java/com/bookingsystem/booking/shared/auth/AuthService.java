package com.bookingsystem.booking.shared.auth;

import java.time.OffsetDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.bookingsystem.booking.shared.auth.dto.LoginRequest;
import com.bookingsystem.booking.shared.auth.dto.RefreshRequest;
import com.bookingsystem.booking.shared.auth.dto.TokenResponse;
import com.bookingsystem.booking.shared.auth.tokens.RefreshTokenService;
import com.bookingsystem.booking.shared.security.CustomUserDetailService;
import com.bookingsystem.booking.shared.security.JwtService;
import com.bookingsystem.booking.shared.security.UserPrincipal;
import com.bookingsystem.booking.user.data.UserRepository;

@Service
public class AuthService {
    
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailService customUserDetailService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;

    public AuthService(AuthenticationManager authenticationManager, CustomUserDetailService customUserDetailService, JwtService jwtService, UserRepository userRepository, RefreshTokenService refreshTokenService) {
        
        this.authenticationManager = authenticationManager;
        this.customUserDetailService = customUserDetailService;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.refreshTokenService = refreshTokenService;
    }

    public TokenResponse login(LoginRequest req) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                req.email(), req.password()));        
    
        var principal = (UserPrincipal) customUserDetailService.loadUserByUsername(req.email());
        var accessToken = jwtService.generateAccessToken(principal);
        var refreshToken = jwtService.generateRefreshToken(principal);

        var user = userRepository.findByEmailIgnoreCase(req.email())
                            .orElseThrow();
        refreshTokenService.store(user, refreshToken, OffsetDateTime.now().plusDays(7));

        return new TokenResponse(accessToken, refreshToken);
    }

    public TokenResponse refresh(RefreshRequest req) {
        var jws = jwtService.parse(req.refreshToken());
        
        if(!"refresh".equals(jws.getBody().get("type")))
            throw new IllegalArgumentException("Invalid refresh token");
        
        var valid = refreshTokenService.findValid(req.refreshToken())
                                    .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token")); 
        refreshTokenService.rotate(valid);

        var userId = Long.parseLong(jws.getBody().getSubject());
        var user = userRepository.findById(userId)
                                .orElseThrow();
        var principal = new UserPrincipal(user);

        var newAccessToken = jwtService.generateAccessToken(principal);
        var newRefreshToken = jwtService.generateRefreshToken(principal);
        refreshTokenService.store(user, newRefreshToken, OffsetDateTime.now().plusDays(7));

        return new TokenResponse(newAccessToken, newRefreshToken);    
    }

    public void logout(Long userId) {
        refreshTokenService.revokeAll(userId);
    }

}
