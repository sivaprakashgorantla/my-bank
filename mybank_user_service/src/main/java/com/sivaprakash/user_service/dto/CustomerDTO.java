package com.sivaprakash.user_service.dto;

import java.time.LocalDateTime;

import com.sivaprakash.user_service.enums.ProfileStatus;

public class CustomerDTO {

    private Long customerId;
    private Long userId;  // Assuming User has an ID
    private ProfileStatus profileStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Default constructor
    public CustomerDTO() {
    }

    public CustomerDTO(Long customerId) {
		super();
		this.customerId = customerId;
	}

	// Constructor to initialize CustomerDTO from Customer entity
    public CustomerDTO(Long customerId, Long userId, ProfileStatus profileStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.customerId = customerId;
        this.userId = userId;
        this.profileStatus = profileStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and setters
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public ProfileStatus getProfileStatus() {
        return profileStatus;
    }

    public void setProfileStatus(ProfileStatus profileStatus) {
        this.profileStatus = profileStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Optionally, you can override toString(), equals(), and hashCode() if needed.
}
