package com.sivaprakash.fund.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
	
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_id_seq")
    @SequenceGenerator(name = "transaction_id_seq", sequenceName = "transaction_id_seq", allocationSize = 1)
    private Long transactionId;

    @Column(name = "from_account_id", nullable = false)
    private String fromAccountId;

    @Column(name = "to_account_id", nullable = false)
    private String toAccountId;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(name = "currency_code", length = 3)
    private String currencyCode = "USD";

    @Column(name = "transaction_type", length = 20)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private TransactionStatus status = TransactionStatus.PENDING;

    @Column(name = "reference_number", unique = true, length = 50)
    private String referenceNumber;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    @Column(length = 200)
    private String description;

    public enum TransactionType {
        TRANSFER, DEPOSIT, WITHDRAWAL
    }

    
    public Transaction() {
		super();
	}


	public Transaction(Long transactionId, String fromAccountId, String toAccountId, BigDecimal amount, String currencyCode,
			TransactionType transactionType, TransactionStatus status, String referenceNumber,
			LocalDateTime transactionDate, String description) {
		super();
		this.transactionId = transactionId;
		this.fromAccountId = fromAccountId;
		this.toAccountId = toAccountId;
		this.amount = amount;
		this.currencyCode = currencyCode;
		this.transactionType = transactionType;
		this.status = status;
		this.referenceNumber = referenceNumber;
		this.transactionDate = transactionDate;
		this.description = description;
	}


	public Long getTransactionId() {
		return transactionId;
	}


	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}


	public String getFromAccountId() {
		return fromAccountId;
	}


	public void setFromAccountId(String fromAccountId) {
		this.fromAccountId = fromAccountId;
	}


	public String getToAccountId() {
		return toAccountId;
	}


	public void setToAccountId(String toAccountId) {
		this.toAccountId = toAccountId;
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


	public TransactionType getTransactionType() {
		return transactionType;
	}


	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}


	public TransactionStatus getStatus() {
		return status;
	}


	public void setStatus(TransactionStatus status) {
		this.status = status;
	}


	public String getReferenceNumber() {
		return referenceNumber;
	}


	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}


	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}


	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", fromAccountId=" + fromAccountId + ", toAccountId="
				+ toAccountId + ", amount=" + amount + ", currencyCode=" + currencyCode + ", transactionType="
				+ transactionType + ", status=" + status + ", referenceNumber=" + referenceNumber + ", transactionDate="
				+ transactionDate + ", description=" + description + "]";
	}


	public enum TransactionStatus {
        PENDING, COMPLETED, FAILED, REVERSED
    }
}