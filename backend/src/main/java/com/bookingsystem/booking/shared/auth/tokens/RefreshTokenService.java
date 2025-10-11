package com.bookingsystem.booking.shared.auth.tokens;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookingsystem.booking.shared.crypto.TokenHasher;
import com.bookingsystem.booking.user.domain.entities.User;



@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenHasher tokenHasher;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, TokenHasher tokenHasher) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.tokenHasher = tokenHasher;
    }

    @Transactional
    public void store(User user, String rawRefreshToken, OffsetDateTime expiresAt) {
        
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setTokenHash(tokenHasher.hash(rawRefreshToken));
        refreshToken.setExpiresAt(expiresAt);
        refreshTokenRepository.save(refreshToken);
    }

    @Transactional(readOnly = true)
    public Optional<RefreshToken> findValid(String raw) {
        
        String hash = tokenHasher.hash(raw);

        return refreshTokenRepository.findByTokenHashAndRevokedFalse(hash)
            .filter(refreshToken -> refreshToken.getExpiresAt().isAfter(OffsetDateTime.now()));
    }

    @Transactional
    public void rotate(RefreshToken oldToken) {
        oldToken.setRevoked(true);
        refreshTokenRepository.save(oldToken);
    }

    @Transactional
    public void revokeAll(Long userId) {
        refreshTokenRepository.revokeAllByUserId(userId);
    }
}