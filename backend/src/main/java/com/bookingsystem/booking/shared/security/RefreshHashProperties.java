package com.bookingsystem.booking.shared.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security.refresh")
public record RefreshHashProperties(
    String hashSecret) 
{}
