package com.devcareeros.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * This class represents the "users" table in MySQL.
 *
 * @Entity tells Hibernate: "this Java class maps to a database table."
 * By default the table name becomes the lowercase class name ("user"),
 * but we override it to "users" with @Table because "user" is a reserved
 * word in some databases.
 */
@Entity
@Table(name = "users")
public class User {

    // @Id marks this field as the PRIMARY KEY of the table.
    // @GeneratedValue tells the database to auto-increment this value
    // every time a new row (a new User object) is saved.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String fullName;

    // unique = true -> no two users can register with the same email
    @Column(nullable = false, unique = true, length = 150)
    private String email;

    // We NEVER store the plain text password. Spring Security's
    // BCryptPasswordEncoder hashes it before it reaches this field.
    // @JsonIgnore makes sure this field is NEVER included when we send a
    // User object back to the frontend as JSON (e.g. from /api/profile).
    @JsonIgnore
    @Column(nullable = false)
    private String password;

    // Very simple role support: "ROLE_USER" or "ROLE_ADMIN"
    @Column(nullable = false, length = 30)
    private String role = "ROLE_USER";

    private String jobTitle;
    private String targetRole;
    private String bio;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // ---------- Constructors ----------

    // A "no-argument constructor" is REQUIRED by Hibernate. Hibernate uses
    // reflection to create objects from database rows, and it needs an
    // empty constructor to do that.
    public User() {
    }

    public User(String fullName, String email, String password) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    // ---------- Getters and Setters ----------
    // Getters/setters are how other classes read and modify the private
    // fields of this object (this is "encapsulation").

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getTargetRole() {
        return targetRole;
    }

    public void setTargetRole(String targetRole) {
        this.targetRole = targetRole;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
