package com.sivaprakash.account.service.entiry;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "account_types")
public class AccountType {

    @Id
    @Column(name = "ACCOUNT_TYPE_ID", nullable = false)
    private Long accountTypeId;

    @Column(name = "DESCRIPTION", nullable = true, length = 255)
    private String description;

    @Column(name = "INTEREST_RATE", nullable = false, precision = 5, scale = 2)
    private BigDecimal interestRate;

    @Column(name = "MAX_WITHDRAWAL_LIMIT", nullable = true, precision = 15, scale = 2)
    private BigDecimal maxWithdrawalLimit;

    @Column(name = "MIN_BALANCE", nullable = false, precision = 15, scale = 2)
    private BigDecimal minBalance;

    @Column(name = "TYPE_NAME", nullable = false, length = 50)
    private String typeName;

    // Getters and setters
    public Long getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(Long accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public BigDecimal getMaxWithdrawalLimit() {
        return maxWithdrawalLimit;
    }

    public void setMaxWithdrawalLimit(BigDecimal maxWithdrawalLimit) {
        this.maxWithdrawalLimit = maxWithdrawalLimit;
    }

    public BigDecimal getMinBalance() {
        return minBalance;
    }

    public void setMinBalance(BigDecimal minBalance) {
        this.minBalance = minBalance;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "AccountType [accountTypeId=" + accountTypeId + ", description=" + description + ", interestRate="
                + interestRate + ", maxWithdrawalLimit=" + maxWithdrawalLimit + ", minBalance=" + minBalance
                + ", typeName=" + typeName + "]";
    }
}
