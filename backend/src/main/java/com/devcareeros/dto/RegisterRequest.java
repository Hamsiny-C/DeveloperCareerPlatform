package com.devcareeros.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * A DTO (Data Transfer Object) is a plain class used ONLY to carry data
 * between the frontend and backend (in the HTTP request/response body).
 *
 * Why not just use the "User" entity directly?
 * Because the entity has fields like "id", "role", "createdAt" that the
 * client should NOT be allowed to set directly. The DTO exposes only what
 * we want to accept from the outside world - this keeps our API safe and
 * our database structure hidden from clients.
 */
public class RegisterRequest {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
