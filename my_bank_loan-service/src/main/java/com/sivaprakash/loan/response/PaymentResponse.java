package com.sivaprakash.loan.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.sivaprakash.loan.enums.PaymentStatus;

public class PaymentResponse {
    private Long paymentId;
    private String referenceNumber;
    private boolean success;
    private String transactionId;
    private PaymentStatus status;
    private LocalDateTime transactionDate;
    private String paymentMethod;
    private BigDecimal amount;
    private String errorMessage;

    // Constructor for simple success/failure response
    public PaymentResponse(Long paymentId, boolean success) {
        this.paymentId = paymentId;
        this.success = success;
        this.status = success ? PaymentStatus.COMPLETED : PaymentStatus.FAILED;
        this.transactionDate = LocalDateTime.now();
    }

    public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public PaymentStatus getStatus() {
		return status;
	}

	public void setStatus(PaymentStatus status) {
		this.status = status;
	}

	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	// Static success response using builder
    public static PaymentResponse successResponse(Long paymentId, String transactionId, BigDecimal amount) {
        return new Builder()
                .paymentId(paymentId)
                .success(true)
                .transactionId(transactionId)
                .status(PaymentStatus.COMPLETED)
                .transactionDate(LocalDateTime.now())
                .amount(amount)
                .build();
    }

    // Static failure response using builder
    public static PaymentResponse failureResponse(String errorMessage) {
        return new Builder()
                .success(false)
                .status(PaymentStatus.FAILED)
                .errorMessage(errorMessage)
                .transactionDate(LocalDateTime.now())
                .build();
    }

    // Builder class
    public static class Builder {
        private Long paymentId;
        private String referenceNumber;
        private boolean success;
        private String transactionId;
        private PaymentStatus status;
        private LocalDateTime transactionDate;
        private String paymentMethod;
        private BigDecimal amount;
        private String errorMessage;

        public Builder paymentId(Long paymentId) {
            this.paymentId = paymentId;
            return this;
        }

        public Builder referenceNumber(String referenceNumber) {
            this.referenceNumber = referenceNumber;
            return this;
        }

        public Builder success(boolean success) {
            this.success = success;
            return this;
        }

        public Builder transactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public Builder status(PaymentStatus status) {
            this.status = status;
            return this;
        }

        public Builder transactionDate(LocalDateTime transactionDate) {
            this.transactionDate = transactionDate;
            return this;
        }

        public Builder paymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder errorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public PaymentResponse build() {
            PaymentResponse paymentResponse = new PaymentResponse(this.paymentId, this.success);
            paymentResponse.referenceNumber = this.referenceNumber;
            paymentResponse.transactionId = this.transactionId;
            paymentResponse.status = this.status;
            paymentResponse.transactionDate = this.transactionDate;
            paymentResponse.paymentMethod = this.paymentMethod;
            paymentResponse.amount = this.amount;
            paymentResponse.errorMessage = this.errorMessage;
            return paymentResponse;
        }
    }
}
