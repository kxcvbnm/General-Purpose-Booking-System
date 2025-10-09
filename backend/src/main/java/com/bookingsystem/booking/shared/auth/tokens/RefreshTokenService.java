package com.bookingsystem.booking.shared.auth.tokens;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookingsystem.booking.shared.security.PasswordHasher;
import com.bookingsystem.booking.user.domain.entities.User;



@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordHasher passwordHasher;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, PasswordHasher passwordHasher) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordHasher = passwordHasher;
    }

    @Transactional
    public void store(User user, String rawRefreshToken, OffsetDateTime expiresAt) {
        
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setTokenHash(passwordHasher.hash(rawRefreshToken));
        refreshToken.setExpiresAt(expiresAt);
        refreshTokenRepository.save(refreshToken);
    }

    @Transactional(readOnly = true)
    public Optional<RefreshToken> findValid(String raw) {
        
        String hash = passwordHasher.hash(raw);

        return refreshTokenRepository.findByTokenHashAndRevokedFalse(hash)
            .filter(refreshToken -> refreshToken.getExpiresAt().isAfter(OffsetDateTime.now()));
    }

    @Transactional
    public void rotate(RefreshToken oldToken) {
        oldToken.setRevoked(true);
        refreshTokenRepository.save(oldToken);
    }

    public void revokeAll(Long userId) {
        refreshTokenRepository.revokeAllByUserId(userId);
    }
}