package com.sivaprakash.loan.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.sivaprakash.loan.enums.LoanStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "loan_applications")
public class LoanApplication {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loan_id_seq")
	@SequenceGenerator(name = "loan_id_seq", sequenceName = "loan_id_seq", allocationSize = 1)
	private Long loanId;
	private Long customerId;
	private Long productId;
	private BigDecimal loanAmount;
	@Enumerated(EnumType.STRING)
	private LoanType loanType;
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
	private String loanPurpose;
	private BigDecimal processingFee;
	private Boolean processingFeePaid;
	private String referenceNumber;

	private String remarkInput;

	public LoanApplication() {
		super();
	}

	public LoanApplication(Long loanId, Long customerId, Long productId, BigDecimal loanAmount, LoanType loanType,
			BigDecimal interestRate, Integer termMonths, LoanStatus status, LocalDateTime applicationDate,
			LocalDateTime approvalDate, LocalDateTime disbursementDate, LocalDateTime lastPaymentDate,
			LocalDateTime nextPaymentDate, BigDecimal remainingAmount, String loanPurpose, BigDecimal processingFee,
			Boolean processingFeePaid, String referenceNumber, String remarkInput) {
		super();
		this.loanId = loanId;
		this.customerId = customerId;
		this.productId = productId;
		this.loanAmount = loanAmount;
		this.loanType = loanType;
		this.interestRate = interestRate;
		this.termMonths = termMonths;
		this.status = status;
		this.applicationDate = applicationDate;
		this.approvalDate = approvalDate;
		this.disbursementDate = disbursementDate;
		this.lastPaymentDate = lastPaymentDate;
		this.nextPaymentDate = nextPaymentDate;
		this.remainingAmount = remainingAmount;
		this.loanPurpose = loanPurpose;
		this.processingFee = processingFee;
		this.processingFeePaid = processingFeePaid;
		this.referenceNumber = referenceNumber;
		this.remarkInput = remarkInput;
	}

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
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

	public String getLoanPurpose() {
		return loanPurpose;
	}

	public void setLoanPurpose(String loanPurpose) {
		this.loanPurpose = loanPurpose;
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

	public LoanType getLoanType() {
		return loanType;
	}

	public void setLoanType(LoanType loanType) {
		this.loanType = loanType;
	}

	public String getRemarkInput() {
		return remarkInput;
	}

	public void setRemarkInput(String remarkInput) {
		this.remarkInput = remarkInput;
	}

	@Override
	public String toString() {
		return "LoanApplication [loanId=" + loanId + ", customerId=" + customerId + ", productId=" + productId
				+ ", loanAmount=" + loanAmount + ", loanType=" + loanType + ", interestRate=" + interestRate
				+ ", termMonths=" + termMonths + ", status=" + status + ", applicationDate=" + applicationDate
				+ ", approvalDate=" + approvalDate + ", disbursementDate=" + disbursementDate + ", lastPaymentDate="
				+ lastPaymentDate + ", nextPaymentDate=" + nextPaymentDate + ", remainingAmount=" + remainingAmount
				+ ", loanPurpose=" + loanPurpose + ", processingFee=" + processingFee + ", processingFeePaid="
				+ processingFeePaid + ", referenceNumber=" + referenceNumber + ", remarkInput=" + remarkInput + "]";
	}

}