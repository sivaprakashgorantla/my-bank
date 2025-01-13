package com.sivaprakash.auth_service.dto;

public class AuthResponseDTO {
	private String token;
	private Long userId;
	private String username;
	private String customerId;
	public AuthResponseDTO(String token, Long userId, String username, String customerId) {
		super();
		this.token = token;
		this.userId = userId;
		this.username = username;
		this.customerId = customerId;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	@Override
	public String toString() {
		return "AuthResponseDTO [token=" + token + ", userId=" + userId + ", username=" + username + ", customerId="
				+ customerId + "]";
	}

}