package com.sivaprakash.user_service.dto;

public class CustomerProfileUpdateRequest {
	private String customerId;
	private String username;

	public CustomerProfileUpdateRequest(String customerId, String username) {
		super();
		this.customerId = customerId;
		this.username = username;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "CustomerProfileUpdateRequest [customerId=" + customerId + ", username=" + username + "]";
	}

}