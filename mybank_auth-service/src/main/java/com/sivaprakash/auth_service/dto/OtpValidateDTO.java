package com.sivaprakash.auth_service.dto;

public class OtpValidateDTO {
	private Long userId;
	private String otp;
	private String phoneNumber;

	public OtpValidateDTO(Long userId, String otp, String phoneNumber) {
		super();
		this.userId = userId;
		this.otp = otp;
		this.phoneNumber = phoneNumber;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

}
