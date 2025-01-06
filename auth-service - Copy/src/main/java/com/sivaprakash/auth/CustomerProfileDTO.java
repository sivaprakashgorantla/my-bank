package com.sivaprakash.auth;

public class CustomerProfileDTO {
	
//can you declare username and password and role here , constructor and getter and setter

	private Long userId;
	private String username;
	private String password;
	private String role;

	public CustomerProfileDTO() {
	}

	
	public CustomerProfileDTO( String username, String password, String role) {

		this.username = username;
		this.password = password;
		this.role = role;
	}

	public CustomerProfileDTO(Long userId, String username, String password, String role) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.role = role;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	
}