package com.sivaprakash.user_service.service;

import com.sivaprakash.user_service.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpServiceImpl implements OtpService {

    // In-memory store for OTPs. In production, use Redis or similar
    private  Map<String, OtpData> otpStore = new ConcurrentHashMap<>();
    
    @Autowired
    private  JavaMailSender emailSender;
    
    // OTP configuration
    private static final int OTP_LENGTH = 6;
    private static final long OTP_VALID_DURATION = 5 * 60 * 1000; // 5 minutes in milliseconds

    @Autowired
    public OtpServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public String generateOtp(String username) {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();
        
        // Generate random 6-digit OTP
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10));
        }
        
        // Store OTP with timestamp
        otpStore.put(username, new OtpData(otp.toString(), System.currentTimeMillis()));
        
        return otp.toString();
    }

    @Override
    public void sendOtp(String mobileNo, String emailId, String otp) {
        // Send OTP via email
        sendEmailOtp(emailId, otp);
        
        // Send OTP via SMS
        sendSmsOtp(mobileNo, otp);
    }

    @Override
    public boolean validateOtp(String username, String otp) {
        OtpData otpData = otpStore.get(username);
        
        if (otpData == null) {
            return false;
        }
        
        // Check if OTP has expired
        if (System.currentTimeMillis() - otpData.getTimestamp() > OTP_VALID_DURATION) {
            otpStore.remove(username);
            return false;
        }
        
        // Validate OTP
        boolean isValid = otpData.getOtp().equals(otp);
        
        // Remove OTP after validation (one-time use)
        if (isValid) {
            otpStore.remove(username);
        }
        
        return isValid;
    }

    private void sendEmailOtp(String emailId, String otp) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailId);
            message.setSubject("Your OTP for Profile Verification");
            message.setText("Your OTP is: " + otp + "\nThis OTP will expire in 5 minutes.");
            
            //emailSender.send(message);
        } catch (Exception e) {
            // Log the error and handle appropriately
            throw new RuntimeException("Failed to send OTP via email", e);
        }
    }

    private void sendSmsOtp(String mobileNo, String otp) {
        // Implement SMS sending logic here
        // You can integrate with SMS gateway services like Twilio, AWS SNS, etc.
        // For now, just logging the OTP
        System.out.println("Sending OTP via SMS to " + mobileNo + ": " + otp);
    }

    // Inner class to store OTP data
    private static class OtpData {
        private final String otp;
        private final long timestamp;

        public OtpData(String otp, long timestamp) {
            this.otp = otp;
            this.timestamp = timestamp;
        }

        public String getOtp() {
            return otp;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }
    
    // Method to cleanup expired OTPs
    public void cleanupExpiredOtps() {
        long currentTime = System.currentTimeMillis();
        otpStore.entrySet().removeIf(entry -> 
            currentTime - entry.getValue().getTimestamp() > OTP_VALID_DURATION);
    }
}