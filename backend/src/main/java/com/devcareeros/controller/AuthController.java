package com.devcareeros.controller;

import com.devcareeros.dto.JwtResponse;
import com.devcareeros.dto.LoginRequest;
import com.devcareeros.dto.RegisterRequest;
import com.devcareeros.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @RestController = @Controller + @ResponseBody, meaning every method's
 * return value is automatically converted to JSON and written directly
 * into the HTTP response body (instead of looking for an HTML view).
 *
 * @RequestMapping("/api/auth") means every endpoint in this class starts
 * with that path, e.g. POST /api/auth/register.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // @PostMapping("/register") handles: POST http://localhost:8080/api/auth/register
    // @RequestBody tells Spring: "take the JSON from the request body and
    // convert it into a RegisterRequest object for me."
    // @Valid triggers the validation annotations (@NotBlank, @Email, etc.)
    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@Valid @RequestBody RegisterRequest request) {
        JwtResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {
        JwtResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
