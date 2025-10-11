package com.bookingsystem.booking.shared.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bookingsystem.booking.shared.crypto.HmacSha256TokenHasher;
import com.bookingsystem.booking.shared.crypto.TokenHasher;
import com.bookingsystem.booking.shared.security.JwtProperties;
import com.bookingsystem.booking.shared.security.RefreshHashProperties;

@Configuration
@EnableConfigurationProperties({JwtProperties.class, RefreshHashProperties.class})
public class CryptoConfig {
    
    @Bean
    public TokenHasher refreshTokenHasher(RefreshHashProperties props) {
        return new HmacSha256TokenHasher(props.hashSecret());
    }
}
