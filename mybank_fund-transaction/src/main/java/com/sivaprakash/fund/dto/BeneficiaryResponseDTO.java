package com.sivaprakash.fund.dto;


public class BeneficiaryResponseDTO {
	private Long beneficiaryId;
	private String beneficiaryName;
	private String beneficiaryAccountNumber;
	private String beneficiaryBankCode;
	private String beneficiaryEmail;
	private String status;
	private String referenceNumber;
	private String message;

	public BeneficiaryResponseDTO() {
		super();
	}

	public BeneficiaryResponseDTO(Long beneficiaryId, String beneficiaryName, String beneficiaryAccountNumber,
			String beneficiaryBankCode, String beneficiaryEmail, String status, String referenceNumber,
			String message) {
		super();
		this.beneficiaryId = beneficiaryId;
		this.beneficiaryName = beneficiaryName;
		this.beneficiaryAccountNumber = beneficiaryAccountNumber;
		this.beneficiaryBankCode = beneficiaryBankCode;
		this.beneficiaryEmail = beneficiaryEmail;
		this.status = status;
		this.referenceNumber = referenceNumber;
		this.message = message;
	}

	public Long getBeneficiaryId() {
		return beneficiaryId;
	}

	public void setBeneficiaryId(Long beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
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

	@Override
	public String toString() {
		return "BeneficiaryResponseDTO [beneficiaryId=" + beneficiaryId + ", beneficiaryName=" + beneficiaryName
				+ ", beneficiaryAccountNumber=" + beneficiaryAccountNumber + ", beneficiaryBankCode="
				+ beneficiaryBankCode + ", beneficiaryEmail=" + beneficiaryEmail + ", status=" + status
				+ ", referenceNumber=" + referenceNumber + ", message=" + message + "]";
	}

	
}