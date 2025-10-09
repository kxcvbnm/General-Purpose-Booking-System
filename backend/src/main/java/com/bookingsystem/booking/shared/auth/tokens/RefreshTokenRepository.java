package com.bookingsystem.booking.shared.auth.tokens;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
   
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
        
    Optional<RefreshToken> findByTokenHashAndRevokedFalse(String tokenHash);

    @Modifying
    @Query("update RefreshToken t set t.revoked = true where t.user.id = :userId")
    void revokeAllByUserId(@Param("userId") Long userId);
}

