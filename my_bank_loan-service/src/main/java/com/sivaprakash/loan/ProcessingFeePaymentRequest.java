package com.sivaprakash.loan;

import java.math.BigDecimal;

import com.sivaprakash.loan.enums.PaymentMethod;

import jakarta.validation.ValidationException;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ProcessingFeePaymentRequest {
    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;
    
    @NotNull(message = "Payment amount is required")
    @Positive(message = "Payment amount must be greater than zero")
    private BigDecimal amount;
    
    private String upiId;
    private String cardNumber;
    private String cardExpiryMonth;
    private String cardExpiryYear;
    private String cvv;
    private String accountNumber;
    private String ifscCode;

    
	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}


	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}


	public BigDecimal getAmount() {
		return amount;
	}


	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}


	public String getUpiId() {
		return upiId;
	}


	public void setUpiId(String upiId) {
		this.upiId = upiId;
	}


	public String getCardNumber() {
		return cardNumber;
	}


	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}


	public String getCardExpiryMonth() {
		return cardExpiryMonth;
	}


	public void setCardExpiryMonth(String cardExpiryMonth) {
		this.cardExpiryMonth = cardExpiryMonth;
	}


	public String getCardExpiryYear() {
		return cardExpiryYear;
	}


	public void setCardExpiryYear(String cardExpiryYear) {
		this.cardExpiryYear = cardExpiryYear;
	}


	public String getCvv() {
		return cvv;
	}


	public void setCvv(String cvv) {
		this.cvv = cvv;
	}


	public String getAccountNumber() {
		return accountNumber;
	}


	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}


	public String getIfscCode() {
		return ifscCode;
	}


	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}


	public ProcessingFeePaymentRequest() {
		super();
	}

	
    public ProcessingFeePaymentRequest(PaymentMethod paymentMethod, BigDecimal amount, String upiId, String cardNumber,
			String cardExpiryMonth, String cardExpiryYear, String cvv, String accountNumber, String ifscCode) {
		super();
		this.paymentMethod = paymentMethod;
		this.amount = amount;
		this.upiId = upiId;
		this.cardNumber = cardNumber;
		this.cardExpiryMonth = cardExpiryMonth;
		this.cardExpiryYear = cardExpiryYear;
		this.cvv = cvv;
		this.accountNumber = accountNumber;
		this.ifscCode = ifscCode;
	}


	@AssertTrue(message = "Required payment details missing")
    public boolean isValidPaymentDetails() {
        return switch (paymentMethod) {
            case UPI -> upiId != null && !upiId.isEmpty();
            case CARD -> isValidCardDetails();
            case BANK_TRANSFER -> isValidBankDetails();
            case SAVINGS_ACCOUNT -> accountNumber != null && !accountNumber.isEmpty();
        };
    }
    
    private boolean isValidCardDetails() {
        return cardNumber != null && cardExpiryMonth != null && 
               cardExpiryYear != null && cvv != null;
    }
    
    private boolean isValidBankDetails() {
        return accountNumber != null && ifscCode != null;
    }
    public boolean isProcessingFeePaid(BigDecimal expectedFee) {
        // Validate that the payment amount matches the expected fee
        if (amount == null || amount.compareTo(expectedFee) != 0) {
            throw new ValidationException("Payment amount does not match the required processing fee.");
        }

        // Validate payment details based on the selected payment method
        if (!isValidPaymentDetails()) {
            throw new ValidationException("Invalid payment details for the selected payment method.");
        }

        // If all validations pass, return true (assume payment is successful for simplicity)
        return true;
    }
}
