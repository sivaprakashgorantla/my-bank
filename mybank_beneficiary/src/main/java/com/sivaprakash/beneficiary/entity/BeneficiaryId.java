package com.sivaprakash.beneficiary.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BeneficiaryId implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long constumerId;
    private String beneficiaryAccountNumber;
    private String beneficiaryBankCode;

    public BeneficiaryId() {
    }

    public BeneficiaryId(Long constumerId, String beneficiaryAccountNumber, String beneficiaryBankCode) {
        this.constumerId = constumerId;
        this.beneficiaryAccountNumber = beneficiaryAccountNumber;
        this.beneficiaryBankCode = beneficiaryBankCode;
    }

    public Long getConstumerId() {
		return constumerId;
	}

	public void setConstumerId(Long constumerId) {
		this.constumerId = constumerId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BeneficiaryId that = (BeneficiaryId) o;
        return Objects.equals(constumerId, that.constumerId) &&
                Objects.equals(beneficiaryAccountNumber, that.beneficiaryAccountNumber) &&
                Objects.equals(beneficiaryBankCode, that.beneficiaryBankCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(constumerId, beneficiaryAccountNumber, beneficiaryBankCode);
    }
}
