package com.sivaprakash.account.service.entiry;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_id_seq")
    @SequenceGenerator(name = "account_id_seq", sequenceName = "account_id_seq", allocationSize = 1)
    private Long accountId;

    @Column(name = "customerid", nullable = true)
    private String customerId;

    @Column(name = "account_type", nullable = false)
    private Integer accountType;

    @Column(name = "account_number", unique = true, nullable = false, length = 20)
    private String accountNumber;

    @Column(precision = 15, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(name = "currency_code", length = 3)
    private String currencyCode = "USD";

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private AccountStatus status = AccountStatus.ACTIVE;


	public Account() {
		super();
	}

	public Account(Long accountId, String  customerId, Integer accountType, String accountNumber, BigDecimal balance,
			String currencyCode, LocalDateTime createdAt, LocalDateTime updatedAt, AccountStatus status) {
		super();
		this.accountId = accountId;
		this.customerId = customerId;
		this.accountType = accountType;
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.currencyCode = currencyCode;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.status = status;
	}

    
    public Long getAccountId() {
		return accountId;
	}


	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}



	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Integer getAccountType() {
		return accountType;
	}


	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}


	public String getAccountNumber() {
		return accountNumber;
	}


	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}


	public BigDecimal getBalance() {
		return balance;
	}


	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}


	public String getCurrencyCode() {
		return currencyCode;
	}


	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}


	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}


	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}


	public AccountStatus getStatus() {
		return status;
	}


	public void setStatus(AccountStatus status) {
		this.status = status;
	}


	
	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", customerId=" + customerId + ", accountType=" + accountType
				+ ", accountNumber=" + accountNumber + ", balance=" + balance + ", currencyCode=" + currencyCode
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", status=" + status + "]";
	}



	public enum AccountStatus {
        ACTIVE, INACTIVE, FROZEN, CLOSED
    }
}