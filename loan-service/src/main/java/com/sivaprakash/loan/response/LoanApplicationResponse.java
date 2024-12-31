package com.sivaprakash.loan.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.sivaprakash.loan.entity.LoanApplication;
import com.sivaprakash.loan.enums.LoanStatus;

public class LoanApplicationResponse {
	private Long loanId;
	private String referenceNumber;
	private LoanStatus status;
	private BigDecimal amount;
	private BigDecimal processingFee;
	private String message;
	private LocalDateTime applicationDate;
	private Map<String, String> additionalInfo;

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public LoanStatus getStatus() {
		return status;
	}

	public void setStatus(LoanStatus status) {
		this.status = status;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getProcessingFee() {
		return processingFee;
	}

	public void setProcessingFee(BigDecimal processingFee) {
		this.processingFee = processingFee;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(LocalDateTime applicationDate) {
		this.applicationDate = applicationDate;
	}

	public Map<String, String> getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(Map<String, String> additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	private LoanApplicationResponse(Builder builder) {
		this.loanId = builder.loanId;
		this.referenceNumber = builder.referenceNumber;
		this.status = builder.status;
		this.amount = builder.amount;
		this.processingFee = builder.processingFee;
		this.message = builder.message;
		this.applicationDate = builder.applicationDate;
		this.additionalInfo = builder.additionalInfo;
	}

	public LoanApplicationResponse(Long loanId, String referenceNumber) {
		// TODO Auto-generated constructor stub
		this.loanId = loanId;
		this.referenceNumber = referenceNumber;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Long loanId;
		private String referenceNumber;
		private LoanStatus status;
		private BigDecimal amount;
		private BigDecimal processingFee;
		private String message;
		private LocalDateTime applicationDate;
		private Map<String, String> additionalInfo;

		public Builder loanId(Long loanId) {
			this.loanId = loanId;
			return this;
		}

		public Builder referenceNumber(String referenceNumber) {
			this.referenceNumber = referenceNumber;
			return this;
		}

		public Builder status(LoanStatus status) {
			this.status = status;
			return this;
		}

		public Builder amount(BigDecimal amount) {
			this.amount = amount;
			return this;
		}

		public Builder processingFee(BigDecimal processingFee) {
			this.processingFee = processingFee;
			return this;
		}

		public Builder message(String message) {
			this.message = message;
			return this;
		}

		public Builder applicationDate(LocalDateTime applicationDate) {
			this.applicationDate = applicationDate;
			return this;
		}

		public Builder additionalInfo(Map<String, String> additionalInfo) {
			this.additionalInfo = additionalInfo;
			return this;
		}

		public LoanApplicationResponse build() {
			return new LoanApplicationResponse(this);
		}
	}

	public static LoanApplicationResponse createSuccessResponse(LoanApplication loan, String referenceNumber) {
		return LoanApplicationResponse.builder().loanId(loan.getLoanId()).referenceNumber(referenceNumber)
				.status(loan.getStatus()).amount(loan.getAmount()).processingFee(loan.getProcessingFee())
				.message("Loan application submitted successfully").applicationDate(loan.getApplicationDate())
				.additionalInfo(new HashMap<>()).build();
	}

	public void addAdditionalInfo(String key, String value) {
		if (this.additionalInfo == null) {
			this.additionalInfo = new HashMap<>();
		}
		this.additionalInfo.put(key, value);
	}
}
