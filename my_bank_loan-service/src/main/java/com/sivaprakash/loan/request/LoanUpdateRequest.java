package com.sivaprakash.loan.request;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.ValidationException;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class LoanUpdateRequest {

	@Positive(message = "Loan amount must be greater than zero")
	private BigDecimal amount;

	@Size(min = 5, max = 200, message = "Loan purpose must be between 5 and 200 characters")
	private String loanPurpose;

	@Min(value = 3, message = "Minimum loan term is 3 months")
	@Max(value = 60, message = "Maximum loan term is 60 months")
	private Integer termMonths;

	private Long loanId;

	// Add missing fields if applicable
	private Boolean processingFeePaid; // Whether processing fee is paid

	private String status; // Loan status (e.g., APPROVED, PENDING, CANCELLED)

	private String remarkInput;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getLoanPurpose() {
		return loanPurpose;
	}

	public void setLoanPurpose(String loanPurpose) {
		this.loanPurpose = loanPurpose;
	}

	public Integer getTermMonths() {
		return termMonths;
	}

	public void setTermMonths(Integer termMonths) {
		this.termMonths = termMonths;
	}

	public Boolean getProcessingFeePaid() {
		return processingFeePaid;
	}

	public void setProcessingFeePaid(Boolean processingFeePaid) {
		this.processingFeePaid = processingFeePaid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemarkInput() {
		return remarkInput;
	}

	public void setRemarkInput(String remarkInput) {
		this.remarkInput = remarkInput;
	}

	
	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public void validate() {
		List<String> errors = new ArrayList<>();

		if (amount != null && amount.compareTo(BigDecimal.ZERO) <= 0) {
			errors.add("Loan amount must be greater than zero");
		}

		if (termMonths != null && (termMonths < 3 || termMonths > 60)) {
			errors.add("Loan term must be between 3 and 60 months");
		}

		if (status != null && !isValidStatus(status)) {
			errors.add("Invalid loan status: " + status);
		}

		if (!errors.isEmpty()) {
			throw new ValidationException(String.join(", ", errors));
		}
	}

	private boolean isValidStatus(String status) {
		return List.of("PENDING", "APPROVED", "REJECTED", "CANCELLED").contains(status.toUpperCase());
	}

}
