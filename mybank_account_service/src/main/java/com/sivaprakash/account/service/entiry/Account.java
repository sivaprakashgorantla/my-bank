package com.sivaprakash.account.service.entiry;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;

import com.sivaprakash.account.service.enums.AccountStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "accounts")
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_id_seq")
	@SequenceGenerator(name = "account_id_seq", sequenceName = "account_id_seq", allocationSize = 1)
	private Long accountId;

	@Column(name = "CUSTOMER_ID", nullable = true)
	private Long customerId;

	@ManyToOne
	@JoinColumn(name = "account_type_id", nullable = false)
	private AccountType accountType; // Linking to AccountType entity

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

	public String generateAccountNumber() {
		SecureRandom random = new SecureRandom();
		StringBuilder accountNumber = new StringBuilder("ACC");

		// Add current timestamp in milliseconds for uniqueness
		long timestamp = System.currentTimeMillis();
		accountNumber.append(timestamp);

		// Add a few random digits for additional randomness
		for (int i = 0; i < 3; i++) { // Add 3 random digits
			accountNumber.append(random.nextInt(10));
		}

		return accountNumber.toString();
	}
	public Account() {}
	public Account(Long accountId, Long customerId, AccountType accountType, String accountNumber, BigDecimal balance,
			String currencyCode, LocalDateTime createdAt, LocalDateTime updatedAt, AccountStatus status) {
		this.accountId = accountId;
		this.customerId = customerId;
		this.accountType = accountType; // Set AccountType
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.currencyCode = currencyCode;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.status = status;
	}

	// Getters and setters for Account entity
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
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
}
