package com.devcareeros.service;

import com.devcareeros.dto.JwtResponse;
import com.devcareeros.dto.LoginRequest;
import com.devcareeros.dto.RegisterRequest;
import com.devcareeros.entity.User;
import com.devcareeros.exception.BadRequestException;
import com.devcareeros.repository.UserRepository;
import com.devcareeros.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Contains the actual BUSINESS LOGIC for registering and logging in users.
 * Controllers stay "thin" (they just receive the HTTP request and call
 * this service) - all the real work happens here.
 */
@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    public JwtResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("An account with this email already exists.");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        // Hash the password BEFORE saving it - never store plain text passwords.
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ROLE_USER");

        User savedUser = userRepository.save(user);

        String token = jwtUtil.generateToken(savedUser.getEmail());

        return new JwtResponse(token, savedUser.getId(), savedUser.getFullName(),
                savedUser.getEmail(), savedUser.getRole());
    }

    public JwtResponse login(LoginRequest request) {
        // This line does the actual password check. Spring Security calls
        // our UserDetailsServiceImpl to load the user, then compares the
        // hashed password using PasswordEncoder. If it fails, an exception
        // is thrown automatically (caught by GlobalExceptionHandler).
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Invalid email or password."));

        String token = jwtUtil.generateToken(user.getEmail());

        return new JwtResponse(token, user.getId(), user.getFullName(), user.getEmail(), user.getRole());
    }
}
