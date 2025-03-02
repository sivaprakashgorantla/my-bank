package com.sivaprakash.loan.response;

public class AccountResponse {
	private Long loanId;
	private String accountNumber;

	public AccountResponse(Long loanId, String accountNumber) {
		super();
		this.loanId = loanId;
		this.accountNumber = accountNumber;
	}

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	@Override
	public String toString() {
		return "AccountResponse [loanId=" + loanId + ", accountNumber=" + accountNumber + "]";
	}

	
}
