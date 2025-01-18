package com.sivaprakash.fund.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

//@Entity
//@Table(name = "beneficiaries")
public class Beneficiary {
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "beneficiary_id_seq")
//    @SequenceGenerator(name = "beneficiary_id_seq", sequenceName = "beneficiary_id_seq", allocationSize = 1)
//    private Long beneficiaryId;
//
//    @Column(name = "account_id", nullable = false)
//    private Long accountId;
//
//    @Column(name = "beneficiary_name", nullable = false, length = 100)
//    private String beneficiaryName;
//
//    @Column(name = "beneficiary_account_number", nullable = false, length = 20)
//    private String beneficiaryAccountNumber;
//
//    @Column(name = "beneficiary_bank_code", length = 20)
//    private String beneficiaryBankCode;
//
//    @Column(name = "beneficiary_bank_name", length = 100)
//    private String beneficiaryBankName;
//
//    @Column(name = "beneficiary_email")
//    private String beneficiaryEmail;
//
//    @Column(name = "beneficiary_type", length = 20)
//    @Enumerated(EnumType.STRING)
//    private BeneficiaryType beneficiaryType;
//
//    @Column(length = 50)
//    private String relationship;
//
//    @Column(length = 20)
//    @Enumerated(EnumType.STRING)
//    private BeneficiaryStatus status = BeneficiaryStatus.ACTIVE;
//
//    @Column(name = "daily_transfer_limit", precision = 15, scale = 2)
//    private BigDecimal dailyTransferLimit;
//
//    @Column(name = "created_at")
//    private LocalDateTime createdAt;
//
//    @Column(name = "updated_at")
//    private LocalDateTime updatedAt;
//
//    @Column(name = "last_transfer_date")
//    private LocalDateTime lastTransferDate;
//
//    @PrePersist
//    protected void onCreate() {
//        createdAt = LocalDateTime.now();
//        updatedAt = LocalDateTime.now();
//    }
//
//    @PreUpdate
//    protected void onUpdate() {
//        updatedAt = LocalDateTime.now();
//    }
//
//    
//    public Beneficiary() {
//		super();
//	}
//
//    
//
//	public Beneficiary(Long beneficiaryId, Long accountId, String beneficiaryName, String beneficiaryAccountNumber,
//			String beneficiaryBankCode, String beneficiaryBankName, String beneficiaryEmail,
//			BeneficiaryType beneficiaryType, String relationship, BeneficiaryStatus status,
//			BigDecimal dailyTransferLimit, LocalDateTime createdAt, LocalDateTime updatedAt,
//			LocalDateTime lastTransferDate) {
//		super();
//		this.beneficiaryId = beneficiaryId;
//		this.accountId = accountId;
//		this.beneficiaryName = beneficiaryName;
//		this.beneficiaryAccountNumber = beneficiaryAccountNumber;
//		this.beneficiaryBankCode = beneficiaryBankCode;
//		this.beneficiaryBankName = beneficiaryBankName;
//		this.beneficiaryEmail = beneficiaryEmail;
//		this.beneficiaryType = beneficiaryType;
//		this.relationship = relationship;
//		this.status = status;
//		this.dailyTransferLimit = dailyTransferLimit;
//		this.createdAt = createdAt;
//		this.updatedAt = updatedAt;
//		this.lastTransferDate = lastTransferDate;
//	}
//
//	public Long getBeneficiaryId() {
//		return beneficiaryId;
//	}
//
//	public void setBeneficiaryId(Long beneficiaryId) {
//		this.beneficiaryId = beneficiaryId;
//	}
//
//	public Long getAccountId() {
//		return accountId;
//	}
//
//	public void setAccountId(Long accountId) {
//		this.accountId = accountId;
//	}
//
//	public String getBeneficiaryName() {
//		return beneficiaryName;
//	}
//
//	public void setBeneficiaryName(String beneficiaryName) {
//		this.beneficiaryName = beneficiaryName;
//	}
//
//	public String getBeneficiaryAccountNumber() {
//		return beneficiaryAccountNumber;
//	}
//
//	public void setBeneficiaryAccountNumber(String beneficiaryAccountNumber) {
//		this.beneficiaryAccountNumber = beneficiaryAccountNumber;
//	}
//
//	public String getBeneficiaryBankCode() {
//		return beneficiaryBankCode;
//	}
//
//	public void setBeneficiaryBankCode(String beneficiaryBankCode) {
//		this.beneficiaryBankCode = beneficiaryBankCode;
//	}
//
//	public String getBeneficiaryBankName() {
//		return beneficiaryBankName;
//	}
//
//	public void setBeneficiaryBankName(String beneficiaryBankName) {
//		this.beneficiaryBankName = beneficiaryBankName;
//	}
//
//	public String getBeneficiaryEmail() {
//		return beneficiaryEmail;
//	}
//
//	public void setBeneficiaryEmail(String beneficiaryEmail) {
//		this.beneficiaryEmail = beneficiaryEmail;
//	}
//
//	public BeneficiaryType getBeneficiaryType() {
//		return beneficiaryType;
//	}
//
//	public void setBeneficiaryType(BeneficiaryType beneficiaryType) {
//		this.beneficiaryType = beneficiaryType;
//	}
//
//	public String getRelationship() {
//		return relationship;
//	}
//
//	public void setRelationship(String relationship) {
//		this.relationship = relationship;
//	}
//
//	public BeneficiaryStatus getStatus() {
//		return status;
//	}
//
//	public void setStatus(BeneficiaryStatus status) {
//		this.status = status;
//	}
//
//	public BigDecimal getDailyTransferLimit() {
//		return dailyTransferLimit;
//	}
//
//	public void setDailyTransferLimit(BigDecimal dailyTransferLimit) {
//		this.dailyTransferLimit = dailyTransferLimit;
//	}
//
//	public LocalDateTime getCreatedAt() {
//		return createdAt;
//	}
//
//	public void setCreatedAt(LocalDateTime createdAt) {
//		this.createdAt = createdAt;
//	}
//
//	public LocalDateTime getUpdatedAt() {
//		return updatedAt;
//	}
//
//	public void setUpdatedAt(LocalDateTime updatedAt) {
//		this.updatedAt = updatedAt;
//	}
//
//	public LocalDateTime getLastTransferDate() {
//		return lastTransferDate;
//	}
//
//	public void setLastTransferDate(LocalDateTime lastTransferDate) {
//		this.lastTransferDate = lastTransferDate;
//	}
//
//
//
//	@Override
//	public String toString() {
//		return "Beneficiary [beneficiaryId=" + beneficiaryId + ", accountId=" + accountId + ", beneficiaryName="
//				+ beneficiaryName + ", beneficiaryAccountNumber=" + beneficiaryAccountNumber + ", beneficiaryBankCode="
//				+ beneficiaryBankCode + ", beneficiaryBankName=" + beneficiaryBankName + ", beneficiaryEmail="
//				+ beneficiaryEmail + ", beneficiaryType=" + beneficiaryType + ", relationship=" + relationship
//				+ ", status=" + status + ", dailyTransferLimit=" + dailyTransferLimit + ", createdAt=" + createdAt
//				+ ", updatedAt=" + updatedAt + ", lastTransferDate=" + lastTransferDate + "]";
//	}
//
//
//
//	public enum BeneficiaryType {
//        INTERNAL, EXTERNAL
//    }
//
//    public enum BeneficiaryStatus {
//        ACTIVE, INACTIVE, SUSPENDED
//    }
}