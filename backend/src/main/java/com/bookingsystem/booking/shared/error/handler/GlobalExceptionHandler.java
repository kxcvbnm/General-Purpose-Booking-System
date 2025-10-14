package com.bookingsystem.booking.shared.error.handler;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bookingsystem.booking.shared.error.exception.AccessDeniedException;
import com.bookingsystem.booking.shared.error.exception.BusinessRuleViolationException;
import com.bookingsystem.booking.shared.error.exception.ConflictException;
import com.bookingsystem.booking.shared.error.exception.InvalidTokenException;
import com.bookingsystem.booking.shared.error.exception.NotFoundException;
import com.bookingsystem.booking.shared.error.exception.TokenExpiredException;
import com.bookingsystem.booking.shared.error.exception.UnauthorizedException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(NotFoundException ex) {
        return ResponseEntity.status(404).body(Map.of(
            "error", "not_found",
            "message", ex.getMessage()
        ));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Map<String, Object>> handleConflict(ConflictException ex) {
        return ResponseEntity.status(409).body(Map.of(
            "error", "conflict",
            "message", ex.getMessage()
        ));
    }

    @ExceptionHandler(BusinessRuleViolationException.class)
    public ResponseEntity<Map<String, Object>> handleBusiness(BusinessRuleViolationException ex) {
        return ResponseEntity.status(400).body(Map.of(
            "error", "business_rule_violation",
            "message", ex.getMessage()
        ));
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<Map<String, Object>> handleTokenExpired(TokenExpiredException ex) {
        return ResponseEntity.status(403).body(Map.of(
            "error", "token_expired",
            "message", ex.getMessage()
        ));
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidToken(InvalidTokenException ex) {
        return ResponseEntity.status(401).body(Map.of(
            "error", "invalid_token",
            "message", ex.getMessage()
        ));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity.status(403).body(Map.of(
            "error", "invalid_token",
            "message", ex.getMessage()
        ));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(UnauthorizedException ex) {
        return ResponseEntity.status(403).body(Map.of(
            "error", "invalid_token",
            "message", ex.getMessage()
        ));
    }
}
