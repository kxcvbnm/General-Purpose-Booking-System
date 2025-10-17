package com.bookingsystem.booking.shared.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(HoursProperties.class)
public class BookingConfig {
    
}
