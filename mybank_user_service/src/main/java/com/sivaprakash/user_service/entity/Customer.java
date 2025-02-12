package com.sivaprakash.user_service.entity;

import java.time.LocalDateTime;

import com.sivaprakash.user_service.enums.ProfileStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_seq")
	@SequenceGenerator(name = "customer_id_seq", sequenceName = "customer_id_seq", allocationSize = 1)
	private Long customerId;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "profile_status")
	@Enumerated(EnumType.STRING)
	private ProfileStatus profileStatus = ProfileStatus.PENDING;

	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	// Set createdAt automatically before persisting the entity
	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now(); // Set createdAt to the current time before insert
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now(); // Update updatedAt on each update
	}

	public Customer() {
	}

	public Customer(Long customerId, User user, ProfileStatus profileStatus, LocalDateTime createdAt,
			LocalDateTime updatedAt) {
		super();
		this.customerId = customerId;
		this.user = user;
		this.profileStatus = profileStatus;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	// Constructors, getters, setters, toString methods

}
