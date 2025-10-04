package com.bookingsystem.booking.exceptionhandlers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public ResponseEntity<Map<String, Object>> handleNotFound(NotFoundException ex) {
        return ResponseEntity.status(404).body(Map.of(
            "error", "not_found",
            "message", ex.getMessage()
        ));
    }
}
