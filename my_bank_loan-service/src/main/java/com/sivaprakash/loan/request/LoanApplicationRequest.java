package com.sivaprakash.loan.request;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class LoanApplicationRequest {
	@NotNull(message = "User ID is required")
	private Long customerId;

	@NotNull(message = "Loan amount is required")
	@Positive(message = "Loan amount must be greater than zero")
	private BigDecimal loanAmount;

	@NotNull(message = "Loan purpose is required")
	@Size(min = 5, max = 200, message = "Loan purpose must be between 5 and 200 characters")
	private String loanPurpose;

	@NotNull(message = "Term months is required")
	@Min(value = 3, message = "Minimum loan term is 3 months")
	@Max(value = 60, message = "Maximum loan term is 60 months")
	private Integer termMonths;

	private Long productId;
	
	private String loanType;

	
	public LoanApplicationRequest() {
		super();
	}

	public LoanApplicationRequest(@NotNull(message = "customer ID is required") Long customerId,
			@NotNull(message = "Loan amount is required") @Positive(message = "Loan amount must be greater than zero") BigDecimal loanAmount,
			@NotNull(message = "Loan purpose is required") @Size(min = 5, max = 200, message = "Loan purpose must be between 5 and 200 characters") String loanPurpose,
			@NotNull(message = "Term months is required") @Min(value = 3, message = "Minimum loan term is 3 months")
			@Max(value = 60, message = "Maximum loan term is 60 months") Integer termMonths,
			Long productId,
			String loanType) {
		super();
		this.customerId = customerId;
		this.loanAmount = loanAmount;
		this.loanPurpose = loanPurpose;
		this.termMonths = termMonths;
		this.productId = productId;
		this.loanType = loanType;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}


	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
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

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	
	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}


	public static class CustomerDetails {
		private String employmentType;
		private BigDecimal monthlyIncome;
		private Integer creditScore;
		private List<String> existingLoans;

		public CustomerDetails() {
			super();
		}

		public CustomerDetails(String employmentType, BigDecimal monthlyIncome, Integer creditScore,
				List<String> existingLoans) {
			super();
			this.employmentType = employmentType;
			this.monthlyIncome = monthlyIncome;
			this.creditScore = creditScore;
			this.existingLoans = existingLoans;
		}

		public String getEmploymentType() {
			return employmentType;
		}

		public void setEmploymentType(String employmentType) {
			this.employmentType = employmentType;
		}

		public BigDecimal getMonthlyIncome() {
			return monthlyIncome;
		}

		public void setMonthlyIncome(BigDecimal monthlyIncome) {
			this.monthlyIncome = monthlyIncome;
		}

		public Integer getCreditScore() {
			return creditScore;
		}

		public void setCreditScore(Integer creditScore) {
			this.creditScore = creditScore;
		}

		public List<String> getExistingLoans() {
			return existingLoans;
		}

		public void setExistingLoans(List<String> existingLoans) {
			this.existingLoans = existingLoans;
		}
	}
}

// ProcessingFeePaymentRequest.java
