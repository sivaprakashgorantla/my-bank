package com.sivaprakash.account.service.entiry;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "account_types")
public class AccountType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_type_id_seq")
    @SequenceGenerator(name = "account_type_id_seq", sequenceName = "account_type_id_seq", allocationSize = 1)
    private Integer accountTypeId;

    @Column(name = "type_name", unique = true, nullable = false, length = 50)
    private String typeName;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "interest_rate", precision = 5, scale = 2, nullable = false)
    private BigDecimal interestRate = BigDecimal.ZERO;

    @Column(name = "min_balance", precision = 15, scale = 2, nullable = false)
    private BigDecimal minBalance = BigDecimal.ZERO;

    @Column(name = "max_withdrawal_limit", precision = 15, scale = 2)
    private BigDecimal maxWithdrawalLimit;

    public AccountType() {
        super();
    }

    public AccountType(String typeName, String description, BigDecimal interestRate, BigDecimal minBalance, BigDecimal maxWithdrawalLimit) {
        this.typeName = typeName;
        this.description = description;
        this.interestRate = interestRate;
        this.minBalance = minBalance;
        this.maxWithdrawalLimit = maxWithdrawalLimit;
    }

    public Integer getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(Integer accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public BigDecimal getMinBalance() {
        return minBalance;
    }

    public void setMinBalance(BigDecimal minBalance) {
        this.minBalance = minBalance;
    }

    public BigDecimal getMaxWithdrawalLimit() {
        return maxWithdrawalLimit;
    }

    public void setMaxWithdrawalLimit(BigDecimal maxWithdrawalLimit) {
        this.maxWithdrawalLimit = maxWithdrawalLimit;
    }

    @Override
    public String toString() {
        return "AccountType [accountTypeId=" + accountTypeId + ", typeName=" + typeName +
                ", description=" + description + ", interestRate=" + interestRate +
                ", minBalance=" + minBalance + ", maxWithdrawalLimit=" + maxWithdrawalLimit + "]";
    }
}
