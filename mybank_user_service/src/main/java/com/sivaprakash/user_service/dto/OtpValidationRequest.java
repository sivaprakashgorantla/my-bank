package com.sivaprakash.user_service.dto;

public class OtpValidationRequest {
	private String otp;
	private String phoneNumber;
	private String username;

	public OtpValidationRequest(String otp, String phoneNumber, String username) {
		super();
		this.otp = otp;
		this.phoneNumber = phoneNumber;
		this.username = username;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}