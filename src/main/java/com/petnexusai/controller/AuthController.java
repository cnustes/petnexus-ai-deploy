package com.petnexusai.controller;

import com.petnexusai.dto.AuthResponse;
import com.petnexusai.dto.LoginRequest;
import com.petnexusai.dto.RegisterRequest;
import com.petnexusai.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user registration and login")
public class AuthController {

    private final AuthService authService;

    /**
     * Handles the user registration request.
     * @param request The registration request object.
     * @return A ResponseEntity containing the authentication token.
     */
    @Operation(summary = "Register a new user", description = "Creates a new user account and returns a JWT token.")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    /**
     * Handles the user login request.
     * @param request The login request object.
     * @return A ResponseEntity containing the authentication token.
     */
    @Operation(summary = "Authenticate a user", description = "Authenticates a user with email and password, and returns a JWT token.")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}