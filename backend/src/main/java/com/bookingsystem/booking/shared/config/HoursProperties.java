package com.bookingsystem.booking.shared.config;

import java.time.LocalTime;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "booking.hours")
public record HoursProperties(

    String timezone,
    LocalTime open,
    LocalTime close
) {}
