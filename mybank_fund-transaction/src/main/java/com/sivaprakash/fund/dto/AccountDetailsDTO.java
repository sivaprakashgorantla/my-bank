package com.sivaprakash.fund.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class AccountDetailsDTO {

	private Long accountId;

	private String customerId;

	private String accountType;

	private String accountNumber;

	private BigDecimal balance;
	private String currencyCode;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	private String status;

	public AccountDetailsDTO() {
		super();
	}

	public AccountDetailsDTO(Long accountId, String customerId, String accountType, String accountNumber,
			BigDecimal balance, String currencyCode, LocalDateTime createdAt, LocalDateTime updatedAt, String status) {
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

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "AccountDetailsDTO [accountId=" + accountId + ", customerId=" + customerId + ", accountType="
				+ accountType + ", accountNumber=" + accountNumber + ", balance=" + balance + ", currencyCode="
				+ currencyCode + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", status=" + status + "]";
	}

}