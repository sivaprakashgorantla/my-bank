package com.sivaprakash.user_service.service;

import org.springframework.stereotype.Component;

public interface OtpService {
	String generateOtp(String username);

	void sendOtp(String mobileNo, String emailId, String otp);

	boolean validateOtp(String username, String otp);
}