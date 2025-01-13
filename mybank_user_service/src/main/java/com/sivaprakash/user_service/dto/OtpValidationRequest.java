package com.sivaprakash.user_service.dto;

public class OtpValidationRequest {
	private String otp;
	private String username;

	public OtpValidationRequest(String otp, String username) {
		super();
		this.otp = otp;
		this.username = username;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "OtpValidationRequest [otp=" + otp + ", username=" + username + "]";
	}

}