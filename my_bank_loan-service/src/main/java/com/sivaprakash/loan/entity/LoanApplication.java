package com.sivaprakash.loan.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import com.sivaprakash.loan.enums.*;
@Entity
public class LoanApplication {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loan_id_seq")
	@SequenceGenerator(name = "loan_id_seq", sequenceName = "loan_id_seq", allocationSize = 1)
	private Long loanId;
	private Long userId;
	private Long productId;
	private BigDecimal amount;
	private BigDecimal interestRate;
	private Integer termMonths;
	@Enumerated(EnumType.STRING)
	private LoanStatus status;
	private LocalDateTime applicationDate;
	private LocalDateTime approvalDate;
	private LocalDateTime disbursementDate;
	private LocalDateTime lastPaymentDate;
	private LocalDateTime nextPaymentDate;
	private BigDecimal remainingAmount;
	private String purpose;
	private BigDecimal processingFee;
	private Boolean processingFeePaid;
	private String referenceNumber;

	public LoanApplication() {
		super();
	}

	public LoanApplication(Long loanId, Long userId, Long productId, BigDecimal amount, BigDecimal interestRate,
			Integer termMonths, LoanStatus status, LocalDateTime applicationDate, LocalDateTime approvalDate,
			LocalDateTime disbursementDate, LocalDateTime lastPaymentDate, LocalDateTime nextPaymentDate,
			BigDecimal remainingAmount, String purpose, BigDecimal processingFee, Boolean processingFeePaid) {
		super();
		this.loanId = loanId;
		this.userId = userId;
		this.productId = productId;
		this.amount = amount;
		this.interestRate = interestRate;
		this.termMonths = termMonths;
		this.status = status;
		this.applicationDate = applicationDate;
		this.approvalDate = approvalDate;
		this.disbursementDate = disbursementDate;
		this.lastPaymentDate = lastPaymentDate;
		this.nextPaymentDate = nextPaymentDate;
		this.remainingAmount = remainingAmount;
		this.purpose = purpose;
		this.processingFee = processingFee;
		this.processingFeePaid = processingFeePaid;
	}

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}

	public Integer getTermMonths() {
		return termMonths;
	}

	public void setTermMonths(Integer termMonths) {
		this.termMonths = termMonths;
	}

	public LoanStatus getStatus() {
		return status;
	}

	public void setStatus(LoanStatus status) {
		this.status = status;
	}

	public LocalDateTime getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(LocalDateTime applicationDate) {
		this.applicationDate = applicationDate;
	}

	public LocalDateTime getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(LocalDateTime approvalDate) {
		this.approvalDate = approvalDate;
	}

	public LocalDateTime getDisbursementDate() {
		return disbursementDate;
	}

	public void setDisbursementDate(LocalDateTime disbursementDate) {
		this.disbursementDate = disbursementDate;
	}

	public LocalDateTime getLastPaymentDate() {
		return lastPaymentDate;
	}

	public void setLastPaymentDate(LocalDateTime lastPaymentDate) {
		this.lastPaymentDate = lastPaymentDate;
	}

	public LocalDateTime getNextPaymentDate() {
		return nextPaymentDate;
	}

	public void setNextPaymentDate(LocalDateTime nextPaymentDate) {
		this.nextPaymentDate = nextPaymentDate;
	}

	public BigDecimal getRemainingAmount() {
		return remainingAmount;
	}

	public void setRemainingAmount(BigDecimal remainingAmount) {
		this.remainingAmount = remainingAmount;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public BigDecimal getProcessingFee() {
		return processingFee;
	}

	public void setProcessingFee(BigDecimal processingFee) {
		this.processingFee = processingFee;
	}

	public Boolean getProcessingFeePaid() {
		return processingFeePaid;
	}

	public void setProcessingFeePaid(Boolean processingFeePaid) {
		this.processingFeePaid = processingFeePaid;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	
}