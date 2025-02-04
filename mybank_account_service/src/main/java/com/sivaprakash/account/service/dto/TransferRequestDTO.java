package com.sivaprakash.account.service.dto;

import java.math.BigDecimal;

public class TransferRequestDTO {
	private String selectedAccount;
	private String beneficiaryAccountNumber;
	private BigDecimal transferAmount;
	private String beneficiaryId;

	public TransferRequestDTO(String selectedAccount, String beneficiaryAccountNumber, BigDecimal transferAmount,
			String beneficiaryId) {
		super();
		this.selectedAccount = selectedAccount;
		this.beneficiaryAccountNumber = beneficiaryAccountNumber;
		this.transferAmount = transferAmount;
		this.beneficiaryId = beneficiaryId;
	}

	public String getSelectedAccount() {
		return selectedAccount;
	}

	public void setSelectedAccount(String selectedAccount) {
		this.selectedAccount = selectedAccount;
	}

	public String getBeneficiaryAccountNumber() {
		return beneficiaryAccountNumber;
	}

	public void setBeneficiaryAccountNumber(String beneficiaryAccountNumber) {
		this.beneficiaryAccountNumber = beneficiaryAccountNumber;
	}

	public BigDecimal getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(BigDecimal transferAmount) {
		this.transferAmount = transferAmount;
	}

	public String getBeneficiaryId() {
		return beneficiaryId;
	}

	public void setBeneficiaryId(String beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}

	@Override
	public String toString() {
		return "TransferRequestDTO [selectedAccount=" + selectedAccount + ", beneficiaryAccountNumber="
				+ beneficiaryAccountNumber + ", transferAmount=" + transferAmount + ", beneficiaryId=" + beneficiaryId
				+ "]";
	}

}
