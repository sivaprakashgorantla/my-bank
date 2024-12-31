package com.sivaprakash.user.ment.service;
import org.springframework.stereotype.Service;

@Service
public class OTPService {

    public String generateOtp(String phoneNumber, String email) {
        // Generate and send OTP to the registered phone number and email
        // For simplicity, we return a static OTP here
        return "123456";  // Replace with actual OTP generation logic
    }

    public boolean validateOtp(String generatedOtp, String otpEntered) {
        return generatedOtp.equals(otpEntered);
    }
}
