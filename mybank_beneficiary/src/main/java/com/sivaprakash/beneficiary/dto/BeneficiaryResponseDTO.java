package com.sivaprakash.beneficiary.dto;


public class BeneficiaryResponseDTO {
	private Long beneficiaryId;
	private Long userId;
	private String beneficiaryName;
	private String beneficiaryBankName;
	private String beneficiaryAccountNumber;
	private String beneficiaryBankCode;
	private String beneficiaryEmail;
	private String status;
	private String referenceNumber;
	private String message;
	private String beneficiaryType;

	public BeneficiaryResponseDTO() {
		super();
	}

	
	public BeneficiaryResponseDTO(String status, String message) {
		super();
		this.status = status;
		this.message = message;
	}


	public BeneficiaryResponseDTO(Long beneficiaryId, Long userId, String beneficiaryName, String beneficiaryBankName,
			String beneficiaryAccountNumber, String beneficiaryBankCode, String beneficiaryEmail, String status,
			String referenceNumber, String message, String beneficiaryType) {
		super();
		this.beneficiaryId = beneficiaryId;
		this.userId = userId;
		this.beneficiaryName = beneficiaryName;
		this.beneficiaryBankName = beneficiaryBankName;
		this.beneficiaryAccountNumber = beneficiaryAccountNumber;
		this.beneficiaryBankCode = beneficiaryBankCode;
		this.beneficiaryEmail = beneficiaryEmail;
		this.status = status;
		this.referenceNumber = referenceNumber;
		this.message = message;
		this.beneficiaryType = beneficiaryType;
	}


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


	public String getBeneficiaryBankName() {
		return beneficiaryBankName;
	}


	public void setBeneficiaryBankName(String beneficiaryBankName) {
		this.beneficiaryBankName = beneficiaryBankName;
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


	public String getBeneficiaryEmail() {
		return beneficiaryEmail;
	}


	public void setBeneficiaryEmail(String beneficiaryEmail) {
		this.beneficiaryEmail = beneficiaryEmail;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getReferenceNumber() {
		return referenceNumber;
	}


	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String getBeneficiaryType() {
		return beneficiaryType;
	}


	public void setBeneficiaryType(String beneficiaryType) {
		this.beneficiaryType = beneficiaryType;
	}


	@Override
	public String toString() {
		return "BeneficiaryResponseDTO [beneficiaryId=" + beneficiaryId + ", userId=" + userId + ", beneficiaryName="
				+ beneficiaryName + ", beneficiaryBankName=" + beneficiaryBankName + ", beneficiaryAccountNumber="
				+ beneficiaryAccountNumber + ", beneficiaryBankCode=" + beneficiaryBankCode + ", beneficiaryEmail="
				+ beneficiaryEmail + ", status=" + status + ", referenceNumber=" + referenceNumber + ", message="
				+ message + ", beneficiaryType=" + beneficiaryType + "]";
	}

	 
}