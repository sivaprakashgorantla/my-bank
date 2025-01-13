package com.sivaprakash.account.service.dto;

import java.math.BigDecimal;
import java.util.List;

public class AccountDetailsDTO {
	private Long accountId;
	private String accountType;
	private String accountNumber;
	private BigDecimal balance;
	private String currencyCode;
	private String status;

	public AccountDetailsDTO() {
		super();
	}

	public AccountDetailsDTO(Long accountId, String accountType, String accountNumber, BigDecimal balance,
			String currencyCode, String status) {
		super();
		this.accountId = accountId;
		this.accountType = accountType;
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.currencyCode = currencyCode;
		this.status = status;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "AccountDetailsDTO [accountId=" + accountId + ", accountType=" + accountType + ", accountNumber="
				+ accountNumber + ", balance=" + balance + ", currencyCode=" + currencyCode + ", status=" + status
				+  "]";
	}


}