package com.sivaprakash.fund.dto;

import java.math.BigDecimal;

public class TransferRequestDTO {
	private Long customerId;
    private String selectedAccount;
    private BigDecimal transferAmount;
    private Long beneficiaryId;
    private String beneficiaryAccountNumber;
    private String transerType;

    
    public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getSelectedAccount() {
        return selectedAccount;
    }

    public void setSelectedAccount(String selectedAccount) {
        this.selectedAccount = selectedAccount;
    }

	public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

	public Long getBeneficiaryId() {
		return beneficiaryId;
	}

	public void setBeneficiaryId(Long beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}

	
	public String getBeneficiaryAccountNumber() {
		return beneficiaryAccountNumber;
	}

	public void setBeneficiaryAccountNumber(String beneficiaryAccountNumber) {
		this.beneficiaryAccountNumber = beneficiaryAccountNumber;
	}

	public String getTranserType() {
		return transerType;
	}

	public void setTranserType(String transerType) {
		this.transerType = transerType;
	}

	@Override
	public String toString() {
		return "TransferRequestDTO [selectedAccount=" + selectedAccount + ", transferAmount=" + transferAmount
				+ ", beneficiaryId=" + beneficiaryId + ", beneficiaryAccountNumber=" + beneficiaryAccountNumber
				+ ", transerType=" + transerType + "]";
	}

	
}
