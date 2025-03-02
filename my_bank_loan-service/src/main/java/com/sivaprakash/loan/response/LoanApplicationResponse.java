package com.sivaprakash.loan.response;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.sivaprakash.loan.entity.LoanType;
import com.sivaprakash.loan.enums.LoanStatus;

public class LoanApplicationResponse {
    private final Long loanId;
    private final Long customerId;
    private final String customerName;
    private final Long productId;
    private final BigDecimal loanAmount;
    private final LoanType loanType;
    private final BigDecimal interestRate;
    private final Integer termMonths;
    private final LoanStatus status;
    private final LocalDateTime applicationDate;
    private final LocalDateTime approvalDate;
    private final LocalDateTime disbursementDate;
    private final LocalDateTime lastPaymentDate;
    private final LocalDateTime nextPaymentDate;
    private final BigDecimal remainingAmount;
    private final String loanPurpose;
    private final BigDecimal processingFee;
    private final Boolean processingFeePaid;
    private final String referenceNumber;

    private LoanApplicationResponse(Builder builder) {
        this.loanId = builder.loanId;
        this.customerId = builder.customerId;
        this.customerName = builder.customerName;
        this.productId = builder.productId;
        this.loanAmount = builder.loanAmount;
        this.loanType = builder.loanType;
        this.interestRate = builder.interestRate;
        this.termMonths = builder.termMonths;
        this.status = builder.status;
        this.applicationDate = builder.applicationDate;
        this.approvalDate = builder.approvalDate;
        this.disbursementDate = builder.disbursementDate;
        this.lastPaymentDate = builder.lastPaymentDate;
        this.nextPaymentDate = builder.nextPaymentDate;
        this.remainingAmount = builder.remainingAmount;
        this.loanPurpose = builder.loanPurpose;
        this.processingFee = builder.processingFee;
        this.processingFeePaid = builder.processingFeePaid;
        this.referenceNumber = builder.referenceNumber;
    }

    public Long getLoanId() { return loanId; }
    public Long getCustomerId() { return customerId; }
    public String getCustomerName() { return customerName; }
    public Long getProductId() { return productId; }
    public BigDecimal getLoanAmount() { return loanAmount; }
    public LoanType getLoanType() { return loanType; }
    public BigDecimal getInterestRate() { return interestRate; }
    public Integer getTermMonths() { return termMonths; }
    public LoanStatus getStatus() { return status; }
    public LocalDateTime getApplicationDate() { return applicationDate; }
    public LocalDateTime getApprovalDate() { return approvalDate; }
    public LocalDateTime getDisbursementDate() { return disbursementDate; }
    public LocalDateTime getLastPaymentDate() { return lastPaymentDate; }
    public LocalDateTime getNextPaymentDate() { return nextPaymentDate; }
    public BigDecimal getRemainingAmount() { return remainingAmount; }
    public String getLoanPurpose() { return loanPurpose; }
    public BigDecimal getProcessingFee() { return processingFee; }
    public Boolean getProcessingFeePaid() { return processingFeePaid; }
    public String getReferenceNumber() { return referenceNumber; }

    public static class Builder {
        private Long loanId;
        private Long customerId;
        private String customerName;
        private Long productId;
        private BigDecimal loanAmount;
        private LoanType loanType;
        private BigDecimal interestRate;
        private Integer termMonths;
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

        public Builder loanId(Long loanId) { this.loanId = loanId; return this; }
        public Builder customerId(Long customerId) { this.customerId = customerId; return this; }
        public Builder customerName(String customerName) { this.customerName = customerName; return this; }
        public Builder productId(Long productId) { this.productId = productId; return this; }
        public Builder loanAmount(BigDecimal loanAmount) { this.loanAmount = loanAmount; return this; }
        public Builder loanType(LoanType loanType) { this.loanType = loanType; return this; }
        public Builder interestRate(BigDecimal interestRate) { this.interestRate = interestRate; return this; }
        public Builder termMonths(Integer termMonths) { this.termMonths = termMonths; return this; }
        public Builder status(LoanStatus status) { this.status = status; return this; }
        public Builder applicationDate(LocalDateTime applicationDate) { this.applicationDate = applicationDate; return this; }
        public Builder approvalDate(LocalDateTime approvalDate) { this.approvalDate = approvalDate; return this; }
        public Builder disbursementDate(LocalDateTime disbursementDate) { this.disbursementDate = disbursementDate; return this; }
        public Builder lastPaymentDate(LocalDateTime lastPaymentDate) { this.lastPaymentDate = lastPaymentDate; return this; }
        public Builder nextPaymentDate(LocalDateTime nextPaymentDate) { this.nextPaymentDate = nextPaymentDate; return this; }
        public Builder remainingAmount(BigDecimal remainingAmount) { this.remainingAmount = remainingAmount; return this; }
        public Builder loanPurpose(String loanPurpose) { this.loanPurpose = loanPurpose; return this; }
        public Builder processingFee(BigDecimal processingFee) { this.processingFee = processingFee; return this; }
        public Builder processingFeePaid(Boolean processingFeePaid) { this.processingFeePaid = processingFeePaid; return this; }
        public Builder referenceNumber(String referenceNumber) { this.referenceNumber = referenceNumber; return this; }

        public LoanApplicationResponse build() { return new LoanApplicationResponse(this); }
    }
}
