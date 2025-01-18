package com.sivaprakash.account.service.dto;

public class UpdateBalanceResponseDTO {
    private boolean success;  // Whether the balance update succeeded
    private String message;   // Message about the result

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
