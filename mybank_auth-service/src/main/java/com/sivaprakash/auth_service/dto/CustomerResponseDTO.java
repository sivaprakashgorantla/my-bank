package com.sivaprakash.auth_service.dto;

import java.time.LocalDateTime;

public class CustomerResponseDTO {

	private Long customerId;
	private Long userId;
	private String profileStatus;

	public CustomerResponseDTO(Long customerId, Long userId, String profileStatus) {
		super();
		this.customerId = customerId;
		this.userId = userId;
		this.profileStatus = profileStatus;
	}

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

	public String getProfileStatus() {
		return profileStatus;
	}

	public void setProfileStatus(String profileStatus) {
		this.profileStatus = profileStatus;
	}

	@Override
	public String toString() {
		return "CustomerResponseDTO [customerId=" + customerId + ", userId=" + userId + ", profileStatus="
				+ profileStatus + "]";
	}

}
