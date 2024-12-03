package com.project.finnote.builders;

import com.project.finnote.entity.User;
import com.project.finnote.enums.Roles;

import java.time.LocalDateTime;

public class UserBuilder {
    private Integer userId;
    private String username;
    private String password;
    private String email;
    private Roles role;
    private LocalDateTime createdAt;

    public UserBuilder setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public UserBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder setRole(Roles role) {
        this.role = role;
        return this;
    }

    public UserBuilder setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public User createUser() {
        return new User(userId, username, password, email, role, createdAt);
    }
}