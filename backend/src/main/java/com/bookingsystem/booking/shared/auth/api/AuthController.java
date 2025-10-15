package com.bookingsystem.booking.shared.auth.api;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookingsystem.booking.shared.auth.dto.request.LoginRequest;
import com.bookingsystem.booking.shared.auth.dto.request.RefreshRequest;
import com.bookingsystem.booking.shared.auth.dto.request.RegisterRequest;
import com.bookingsystem.booking.shared.auth.dto.response.TokenResponse;
import com.bookingsystem.booking.shared.auth.service.AuthService;
import com.bookingsystem.booking.shared.security.UserPrincipal;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@Valid @RequestBody RegisterRequest req) {
        TokenResponse tokens = authService.register(req); 
        URI location = URI.create("/api/users/me");
        return ResponseEntity.created(location).body(tokens);
    }

    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody LoginRequest req) {
        return authService.login(req);
    }

    @PostMapping("/refresh")
    public TokenResponse refresh(@Valid @RequestBody RefreshRequest req) {
        return authService.refresh(req);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(Authentication auth) {
        UserPrincipal user = (UserPrincipal) auth.getPrincipal();
        authService.logout(user.getId());
        return ResponseEntity.noContent().build();
    }
}
