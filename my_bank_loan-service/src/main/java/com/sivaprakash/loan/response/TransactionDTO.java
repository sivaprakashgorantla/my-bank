package com.sivaprakash.loan.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionDTO {
	private Long transactionId;
	private String transactionType;
	private String counterpartyBank;
	private String counterpartyAccountId;
	private LocalDateTime transactionDate;
	private BigDecimal amount;
	private String currencyCode;
	private String status;
	private String description;

	public TransactionDTO() {
		super();
	}

	public TransactionDTO(Long transactionId, String transactionType, String counterpartyBank,
			String counterpartyAccountId, LocalDateTime transactionDate, BigDecimal amount, String currencyCode,
			String status, String description) {
		super();
		this.transactionId = transactionId;
		this.transactionType = transactionType;
		this.counterpartyBank = counterpartyBank;
		this.counterpartyAccountId = counterpartyAccountId;
		this.transactionDate = transactionDate;
		this.amount = amount;
		this.currencyCode = currencyCode;
		this.status = status;
		this.description = description;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getCounterpartyBank() {
		return counterpartyBank;
	}

	public void setCounterpartyBank(String counterpartyBank) {
		this.counterpartyBank = counterpartyBank;
	}

	public String getCounterpartyAccountId() {
		return counterpartyAccountId;
	}

	public void setCounterpartyAccountId(String counterpartyAccountId) {
		this.counterpartyAccountId = counterpartyAccountId;
	}

	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "TransactionDTO [transactionId=" + transactionId + ", transactionType=" + transactionType
				+ ", counterpartyBank=" + counterpartyBank + ", counterpartyAccountId=" + counterpartyAccountId
				+ ", transactionDate=" + transactionDate + ", amount=" + amount + ", currencyCode=" + currencyCode
				+ ", status=" + status + ", description=" + description + "]";
	}

}
