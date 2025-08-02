package com.petnexusai.config;

import com.petnexusai.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException; // Import this
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

/**
 * Global exception handler for the application.
 * This class catches exceptions thrown from controllers and formats them
 * into a consistent JSON error response.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles ResponseStatusException, which we use for business logic errors
     * like email conflicts or other client-side errors.
     *
     * @param ex The caught ResponseStatusException.
     * @return A ResponseEntity containing a standardized error format.
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex) {
        log.error("💥 ResponseStatusException caught: {}", ex.getReason());
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getStatusCode().value(),
                ex.getReason(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, ex.getStatusCode());
    }

    /**
     * Handles AuthenticationException, which is thrown by Spring Security
     * for any failed login attempt (user not found, bad password, etc.).
     *
     * @param ex The caught AuthenticationException.
     * @return A ResponseEntity with a 401 Unauthorized status and a generic error message.
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex) {
        log.error("🛂 Authentication failed: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "Invalid credentials", // Generic message for security
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}