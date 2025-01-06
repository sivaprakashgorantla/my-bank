package com.sivaprakash.loan;

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
    private Long userId;
    
    @NotNull(message = "Loan amount is required")
    @Positive(message = "Loan amount must be greater than zero")
    private BigDecimal amount;
    
    @NotNull(message = "Loan purpose is required")
    @Size(min = 5, max = 200, message = "Loan purpose must be between 5 and 200 characters")
    private String purpose;
    
    @NotNull(message = "Term months is required")
    @Min(value = 3, message = "Minimum loan term is 3 months")
    @Max(value = 60, message = "Maximum loan term is 60 months")
    private Integer termMonths;
    
    private Long productId;
    
    @Valid
    private CustomerDetails customerDetails;
    
    
    public LoanApplicationRequest(@NotNull(message = "User ID is required") Long userId,
			@NotNull(message = "Loan amount is required") @Positive(message = "Loan amount must be greater than zero") BigDecimal amount,
			@NotNull(message = "Loan purpose is required") @Size(min = 5, max = 200, message = "Loan purpose must be between 5 and 200 characters") String purpose,
			@NotNull(message = "Term months is required") @Min(value = 3, message = "Minimum loan term is 3 months") @Max(value = 60, message = "Maximum loan term is 60 months") Integer termMonths,
			Long productId, @Valid CustomerDetails customerDetails) {
		super();
		this.userId = userId;
		this.amount = amount;
		this.purpose = purpose;
		this.termMonths = termMonths;
		this.productId = productId;
		this.customerDetails = customerDetails;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public BigDecimal getAmount() {
		return amount;
	}


	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}


	public String getPurpose() {
		return purpose;
	}


	public void setPurpose(String purpose) {
		this.purpose = purpose;
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


	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}


	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
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
