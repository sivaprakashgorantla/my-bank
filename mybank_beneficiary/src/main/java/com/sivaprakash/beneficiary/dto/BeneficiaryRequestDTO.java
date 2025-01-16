package com.sivaprakash.beneficiary.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BeneficiaryRequestDTO {

	private Long beneficiaryId;

	@NotNull(message = "Account ID is required.")
	private Long userId;

	@NotBlank(message = "Beneficiary name is required.")
	@Size(max = 100, message = "Beneficiary name cannot exceed 100 characters.")
	private String beneficiaryName;

	@NotBlank(message = "Beneficiary account number is required.")
	@Pattern(regexp = "\\d{9,18}", message = "Account number must be between 9 and 18 digits.")
	private String beneficiaryAccountNumber;

	@NotBlank(message = "Beneficiary bank code is required.")
	@Size(max = 20, message = "Bank code cannot exceed 20 characters.")
	private String beneficiaryBankCode;

	@NotBlank(message = "Beneficiary bank name is required.")
	@Size(max = 100, message = "Bank name cannot exceed 100 characters.")
	private String beneficiaryBankName;

	@Email(message = "Please provide a valid email address.")
	@NotBlank(message = "Beneficiary email is required.")
	private String beneficiaryEmail;

	@NotBlank(message = "Beneficiary type is required.")
	@Pattern(regexp = "INTERNAL|EXTERNAL", message = "Beneficiary type must be either 'INTERNAL' or 'EXTERNAL'.")
	private String beneficiaryType;

	@Size(max = 50, message = "Relationship cannot exceed 50 characters.")
	private String relationship;

	@NotNull(message = "Status is required.")
	private String status ;

	@NotNull(message = "Daily transfer limit is required.")
	@DecimalMin(value = "0.01", message = "Daily transfer limit must be greater than 0.")
	@Digits(integer = 10, fraction = 2, message = "Daily transfer limit must be a valid monetary amount.")
	private BigDecimal dailyTransferLimit;

	private LocalDateTime lastTransferDate;

	// Getters and Setters

	public Long getBeneficiaryId() {
		return beneficiaryId;
	}

	public void setBeneficiaryId(Long beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public String getBeneficiaryAccountNumber() {
		return beneficiaryAccountNumber;
	}

	public void setBeneficiaryAccountNumber(String beneficiaryAccountNumber) {
		this.beneficiaryAccountNumber = beneficiaryAccountNumber;
	}

	public String getBeneficiaryBankCode() {
		return beneficiaryBankCode;
	}

	public void setBeneficiaryBankCode(String beneficiaryBankCode) {
		this.beneficiaryBankCode = beneficiaryBankCode;
	}

	public String getBeneficiaryBankName() {
		return beneficiaryBankName;
	}

	public void setBeneficiaryBankName(String beneficiaryBankName) {
		this.beneficiaryBankName = beneficiaryBankName;
	}

	public String getBeneficiaryEmail() {
		return beneficiaryEmail;
	}

	public void setBeneficiaryEmail(String beneficiaryEmail) {
		this.beneficiaryEmail = beneficiaryEmail;
	}

	public String getBeneficiaryType() {
		return beneficiaryType;
	}

	public void setBeneficiaryType(String beneficiaryType) {
		this.beneficiaryType = beneficiaryType;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getDailyTransferLimit() {
		return dailyTransferLimit;
	}

	public void setDailyTransferLimit(BigDecimal dailyTransferLimit) {
		this.dailyTransferLimit = dailyTransferLimit;
	}

	public LocalDateTime getLastTransferDate() {
		return lastTransferDate;
	}

	public void setLastTransferDate(LocalDateTime lastTransferDate) {
		this.lastTransferDate = lastTransferDate;
	}

	@Override
	public String toString() {
		return "BeneficiaryRequestDTO [beneficiaryId=" + beneficiaryId + ", userId=" + userId
				+ ", beneficiaryName=" + beneficiaryName + ", beneficiaryAccountNumber=" + beneficiaryAccountNumber
				+ ", beneficiaryBankCode=" + beneficiaryBankCode + ", beneficiaryBankName=" + beneficiaryBankName
				+ ", beneficiaryEmail=" + beneficiaryEmail + ", beneficiaryType=" + beneficiaryType + ", relationship="
				+ relationship + ", status=" + status + ", dailyTransferLimit=" + dailyTransferLimit
				+ ", lastTransferDate=" + lastTransferDate + "]";
	}

}
