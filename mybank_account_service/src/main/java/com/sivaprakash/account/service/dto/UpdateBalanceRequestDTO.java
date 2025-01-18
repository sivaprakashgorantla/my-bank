package com.sivaprakash.account.service.dto;

import java.math.BigDecimal;

public class UpdateBalanceRequestDTO {
    private String accountNumber;  // Account number to update
    private BigDecimal amount;     // Amount to credit or debit
    private String transactionType; // Either "CREDIT" or "DEBIT"

    // Getters and Setters
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
