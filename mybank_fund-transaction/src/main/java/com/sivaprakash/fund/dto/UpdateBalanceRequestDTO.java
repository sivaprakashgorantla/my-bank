package com.sivaprakash.fund.dto;

import java.math.BigDecimal;

public class UpdateBalanceRequestDTO {
    private String accountNumber;
    private BigDecimal amount;
    private String transactionType; // Either "CREDIT" or "DEBIT"

    public UpdateBalanceRequestDTO() {}

    public UpdateBalanceRequestDTO(String accountNumber, BigDecimal amount, String transactionType) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.transactionType = transactionType;
    }

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

    @Override
    public String toString() {
        return "UpdateBalanceRequestDTO{" +
                "accountNumber='" + accountNumber + '\'' +
                ", amount=" + amount +
                ", transactionType='" + transactionType + '\'' +
                '}';
    }
}
