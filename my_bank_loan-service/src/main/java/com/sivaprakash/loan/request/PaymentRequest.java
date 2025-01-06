package com.sivaprakash.loan.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sivaprakash.loan.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
public class PaymentRequest {
    @NotNull
    private Long loanId;
    
    @NotNull
    private BigDecimal amount;
    
    @NotNull
    private PaymentMethod paymentMethod;
    
    private String paymentReference;
    private Map<String, String> paymentDetails;
    private String currency = "INR";
    private String description;
    
    @JsonIgnore
    private String ipAddress;
    
  
    private LocalDateTime requestDate = LocalDateTime.now();
    
    public void addPaymentDetail(String key, String value) {
        if (paymentDetails == null) {
            paymentDetails = new HashMap<>();
        }
        paymentDetails.put(key, value);
    }
    
    public PaymentRequest maskSensitiveData() {
        if (paymentDetails != null) {
            Map<String, String> maskedDetails = new HashMap<>();
            paymentDetails.forEach((key, value) -> 
                maskedDetails.put(key, maskValue(key, value)));
            this.paymentDetails = maskedDetails;
        }
        return this;
    }
    
    private String maskValue(String key, String value) {
        if (key.toLowerCase().contains("card") || 
            key.toLowerCase().contains("cvv") || 
            key.toLowerCase().contains("password")) {
            return value.replaceAll(".(?=.{4})", "*");
        }
        return value;
    }

	public PaymentRequest() {
		super();
	}

	public PaymentRequest(@NotNull Long loanId, @NotNull BigDecimal amount, @NotNull PaymentMethod paymentMethod,
			String paymentReference, Map<String, String> paymentDetails, String currency, String description,
			String ipAddress, LocalDateTime requestDate) {
		super();
		this.loanId = loanId;
		this.amount = amount;
		this.paymentMethod = paymentMethod;
		this.paymentReference = paymentReference;
		this.paymentDetails = paymentDetails;
		this.currency = currency;
		this.description = description;
		this.ipAddress = ipAddress;
		this.requestDate = requestDate;
	}

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentReference() {
		return paymentReference;
	}

	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}

	public Map<String, String> getPaymentDetails() {
		return paymentDetails;
	}

	public void setPaymentDetails(Map<String, String> paymentDetails) {
		this.paymentDetails = paymentDetails;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public LocalDateTime getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(LocalDateTime requestDate) {
		this.requestDate = requestDate;
	}

}
