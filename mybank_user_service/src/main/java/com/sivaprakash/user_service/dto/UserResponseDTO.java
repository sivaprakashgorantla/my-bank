package com.sivaprakash.user_service.dto;

import com.sivaprakash.user_service.entity.User.Role;

public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    private Role role;


    public UserResponseDTO() {
    }

    public UserResponseDTO(Long id, String username, String email, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}