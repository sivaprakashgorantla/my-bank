package com.sivaprakash.fund.dto;

public class TransferResponseDTO {
    private boolean success; // Indicates whether the transfer was successful
    private String message;  // A message providing more details about the transfer result

    public TransferResponseDTO() {
    }

    public TransferResponseDTO(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

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

    @Override
    public String toString() {
        return "TransferResponseDTO{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}
