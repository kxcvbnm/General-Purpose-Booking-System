package com.bookingsystem.booking.shared.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    info = @Info(
        contact = @Contact(
            name = "Khunanont Titayanunt",
            email = "khunanont.ti@gmail.com"
        ),
        description = "REST API for rooms and bookings with JWT auth.\r\n" + //
                        " - Time window: bookings between 08:00–19:00, duration 1–2 hours, no overlaps.\r\n" + //
                        " - Roles: USER, ADMIN.\r\n" + //
                        " - Errors use structured payloads with business rule details.",
        title = "Booking System API - Khunanont Titayanunt",
        version = "1.0.0"
    ),
    servers = {
        @Server(
            description = "Local Server",
            url = "http://localhost:8082"
        )
    },
    security = {
        @SecurityRequirement(
            name = "Bearer Authentication"
        )
    }
)
@SecurityScheme(
    name = "Bearer Authentication",
    description = "JWT Authorization header using the Bearer scheme.",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer",
    in = SecuritySchemeIn.HEADER
)
public class OpenAPIConfig {
    
}
