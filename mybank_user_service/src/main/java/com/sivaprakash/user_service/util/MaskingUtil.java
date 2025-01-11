package com.sivaprakash.user_service.util;
public class MaskingUtil {

    public static String maskEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email address");
        }
        
        String[] parts = email.split("@");
        String username = parts[0];
        String domain = parts[1];
        
        if (username.length() <= 2) {
            // If username is very short, mask it entirely
            username = "*".repeat(username.length());
        } else {
            // Mask all but the first and last characters
            username = username.charAt(0) + "*".repeat(username.length() - 2) + username.charAt(username.length() - 1);
        }
        
        return username + "@" + domain;
    }

    public static String maskPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() < 4) {
            throw new IllegalArgumentException("Invalid phone number");
        }
        
        // Show only the last 4 digits
        String visiblePart = phoneNumber.substring(phoneNumber.length() - 4);
        String maskedPart = "*".repeat(phoneNumber.length() - 4);
        
        return maskedPart + visiblePart;
    }
}
