package com.petnexusai.service;

import com.petnexusai.dto.AuthResponse;
import com.petnexusai.dto.LoginRequest;
import com.petnexusai.dto.RegisterRequest;
import com.petnexusai.model.User;
import com.petnexusai.repository.UserRepository;
import com.petnexusai.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus; // Import this
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException; // Import this

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Registers a new user in the system.
     * @param request The registration request containing user details.
     * @return An AuthResponse containing the JWT token.
     * @throws ResponseStatusException if the email is already in use.
     */
    public AuthResponse register(RegisterRequest request) {
        log.info("📝 Attempting to register new user with email: {}", request.getEmail());

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            log.warn("⚠️ Email {} already exists.", request.getEmail());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");
        }
        // --- END OF VALIDATION BLOCK ---

        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        log.info("✅ User registered successfully: {}", user.getEmail());
        return AuthResponse.builder().token(jwtToken).build();
    }

    /**
     * Authenticates an existing user.
     * @param request The login request containing user credentials.
     * @return An AuthResponse containing the JWT token.
     */
    public AuthResponse login(LoginRequest request) {
        log.info("➡️ Attempting to authenticate user: {}", request.getEmail());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        log.info("✅ User authenticated successfully: {}", user.getEmail());
        return AuthResponse.builder().token(jwtToken).build();
    }
}