package com.sivaprakash.account.service.entiry;

import java.security.SecureRandom;

public class AccountNumberGenerator {
    private static final int ACCOUNT_NUMBER_LENGTH = 16;
    private static final String BRANCH_CODE = "1001"; // Example branch code
    private static final String ACCOUNT_TYPE_PREFIX = "10"; // Example: 10 for savings, 20 for checking
    
    public static String generateAccountNumber() {
        SecureRandom random = new SecureRandom();
        StringBuilder accountNumber = new StringBuilder();
        
        // Add branch code (4 digits)
        accountNumber.append(BRANCH_CODE);
        
        // Add account type (2 digits)
        accountNumber.append(ACCOUNT_TYPE_PREFIX);
        
        // Generate random digits for the remaining length
        int remainingLength = ACCOUNT_NUMBER_LENGTH - BRANCH_CODE.length() - ACCOUNT_TYPE_PREFIX.length() - 1;
        for (int i = 0; i < remainingLength; i++) {
            accountNumber.append(random.nextInt(10));
        }
        
        // Add check digit using Luhn algorithm
        int checkDigit = calculateLuhnCheckDigit(accountNumber.toString());
        accountNumber.append(checkDigit);
        
        return accountNumber.toString();
    }
    
    private static int calculateLuhnCheckDigit(String number) {
        int sum = 0;
        boolean alternate = false;
        
        // Process digits from right to left
        for (int i = number.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(number.charAt(i));
            
            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit = (digit % 10) + 1;
                }
            }
            
            sum += digit;
            alternate = !alternate;
        }
        
        // Calculate check digit
        return (10 - (sum % 10)) % 10;
    }
    
    public static boolean validateAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.length() != ACCOUNT_NUMBER_LENGTH) {
            return false;
        }
        
        try {
            // Check if all characters are digits
            if (!accountNumber.matches("\\d+")) {
                return false;
            }
            
            // Verify check digit using Luhn algorithm
            String numberWithoutCheckDigit = accountNumber.substring(0, accountNumber.length() - 1);
            int expectedCheckDigit = calculateLuhnCheckDigit(numberWithoutCheckDigit);
            int actualCheckDigit = Character.getNumericValue(accountNumber.charAt(accountNumber.length() - 1));
            
            return expectedCheckDigit == actualCheckDigit;
        } catch (Exception e) {
            return false;
        }
    }
}