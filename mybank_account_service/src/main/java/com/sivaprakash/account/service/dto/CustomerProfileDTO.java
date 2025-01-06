package com.sivaprakash.account.service.dto;

public class CustomerProfileDTO {
	
	private Long userId;
	
	private boolean isProfileComplete;

	public CustomerProfileDTO(Long userId, boolean isProfileComplete) {
		super();
		this.userId = userId;
		this.isProfileComplete = isProfileComplete;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public boolean isProfileComplete() {
		return isProfileComplete;
	}

	public void setProfileComplete(boolean isProfileComplete) {
		this.isProfileComplete = isProfileComplete;
	}

	@Override
	public String toString() {
		return "CustomerProfileDTO [userId=" + userId + ", isProfileComplete=" + isProfileComplete + "]";
	}

}