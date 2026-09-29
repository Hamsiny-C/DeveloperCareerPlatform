package com.devcareeros.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

/**
 * Represents one skill row, e.g. "Java - 80%".
 * Every Skill belongs to exactly one User -> that's a "Many Skills to One
 * User" relationship, expressed below with @ManyToOne.
 */
@Entity
@Table(name = "skills")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name; // e.g. "Java", "Spring Boot"

    private String category; // e.g. "Backend", "Frontend", "Database", "DevOps"

    private String level; // e.g. "Beginner", "Intermediate", "Advanced"

    @Column(nullable = false)
    private Integer progressPercentage = 0; // 0 - 100

    // This is the foreign key column (user_id) linking this skill to its owner.
    // @ManyToOne means: many Skill rows can point to the same one User.
    // fetch = LAZY means Hibernate only loads the related User from the
    // database when we actually access it (better performance).
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Skill() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(Integer progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
