package com.sivaprakash.user_service.dto;

public class CustomerProfileUpdateRequest {
	private Long customerId;
	private String username;

	public CustomerProfileUpdateRequest(Long customerId, String username) {
		super();
		this.customerId = customerId;
		this.username = username;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
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