package com.sivaprakash.user_service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class UserResponseDTO {
	private Long userId;
	private String username;
	private String password;
	private String email;
	private String phoneNumber;
	private String firstName;
	private String lastName;
	private String dateOfBirth;
	private String address;
	private String otp;
	private String createdAt;
	private String updatedAt;
	private String status;
	private String role; // Default role
	private String customerId;
	public UserResponseDTO() {}
	public UserResponseDTO(Long userId, String username, String password, String email, String phoneNumber,
			String firstName, String lastName, String dateOfBirth, String address, String otp, String createdAt,
			String updatedAt, String status, String role) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.otp = otp;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.status = status;
		this.role = role;
	
	}
	public UserResponseDTO(Long userId, String username, String password, String email, String phoneNumber,
			String firstName, String lastName, String dateOfBirth, String address, String otp, String createdAt,
			String updatedAt, String status, String role, String customerId) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.otp = otp;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.status = status;
		this.role = role;
		this.customerId = customerId;
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
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

}