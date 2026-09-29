package com.devcareeros.dto;

/**
 * What we send back to the client right after a successful login/register.
 * The frontend stores this "token" (usually in localStorage) and attaches
 * it to every future request in the "Authorization: Bearer <token>" header.
 */
public class JwtResponse {

    private String token;
    private Long userId;
    private String fullName;
    private String email;
    private String role;

    public JwtResponse(String token, Long userId, String fullName, String email, String role) {
        this.token = token;
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
