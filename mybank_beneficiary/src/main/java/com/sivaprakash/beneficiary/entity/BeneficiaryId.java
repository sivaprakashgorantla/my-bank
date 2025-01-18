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
	private Long userId;
    private String beneficiaryAccountNumber;
    private String beneficiaryBankCode;

    public BeneficiaryId() {
    }

    public BeneficiaryId(Long userId, String beneficiaryAccountNumber, String beneficiaryBankCode) {
        this.userId = userId;
        this.beneficiaryAccountNumber = beneficiaryAccountNumber;
        this.beneficiaryBankCode = beneficiaryBankCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
        return Objects.equals(userId, that.userId) &&
                Objects.equals(beneficiaryAccountNumber, that.beneficiaryAccountNumber) &&
                Objects.equals(beneficiaryBankCode, that.beneficiaryBankCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, beneficiaryAccountNumber, beneficiaryBankCode);
    }
}
