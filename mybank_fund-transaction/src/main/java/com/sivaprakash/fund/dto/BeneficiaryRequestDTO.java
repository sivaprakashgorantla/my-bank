package com.sivaprakash.fund.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public class BeneficiaryRequestDTO {
	@NotBlank(message = "Beneficiary name is required")
	@Size(min = 2, max = 100, message = "Beneficiary name must be between 2 and 100 characters")
	private String beneficiaryName;

	@NotBlank(message = "Account number is required")
	@Pattern(regexp = "^[0-9]{9,18}$", message = "Invalid account number format")
	private String beneficiaryAccountNumber;

	@NotBlank(message = "IFSC code is required")
	@Pattern(regexp = "^[A-Z]{4}0[A-Z0-9]{6}$", message = "Invalid IFSC code format")
	private String beneficiaryBankCode;

	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	private String beneficiaryEmail;

	@SuppressWarnings("deprecation")
	@NotNull(message = "Account ID is required")
	private Long accountId;

	public BeneficiaryRequestDTO(String beneficiaryName, String beneficiaryAccountNumber, String beneficiaryBankCode,
			String beneficiaryEmail, Long accountId) {
		super();
		this.beneficiaryName = beneficiaryName;
		this.beneficiaryAccountNumber = beneficiaryAccountNumber;
		this.beneficiaryBankCode = beneficiaryBankCode;
		this.beneficiaryEmail = beneficiaryEmail;
		this.accountId = accountId;
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

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

}
