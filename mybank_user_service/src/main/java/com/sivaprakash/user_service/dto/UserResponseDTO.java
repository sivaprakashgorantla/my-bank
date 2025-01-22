package com.sivaprakash.user_service.dto;

import com.sivaprakash.user_service.entity.User.Role;
import com.sivaprakash.user_service.entity.User.UserStatus;

public class UserResponseDTO {
	private Long userId;
	private String username;
	private String firstName;
	private String lastName;
	private String password;
	private String email;
	private String customerId;
	private String phoneNumber;
	private String status;

	public UserResponseDTO(Long userId, String username, String firstName, String lastName, String password,
			String email, String customerId, String phoneNumber, String userStatus) {
		super();
		this.userId = userId;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.customerId = customerId;
		this.phoneNumber = phoneNumber;
		this.status = userStatus;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "UserResponseDTO [userId=" + userId + ", username=" + username + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", password=" + password + ", email=" + email + ", customerId="
				+ customerId + ", phoneNumber=" + phoneNumber + ", status=" + status + "]";
	}

}