package com.sivaprakash.account.service.dto;

import java.util.List;

public class AccountResponseDTO {
    private String message;
    private List<AccountDetailsDTO> accounts;
	public AccountResponseDTO(String message, List<AccountDetailsDTO> accounts) {
		super();
		this.message = message;
		this.accounts = accounts;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<AccountDetailsDTO> getAccounts() {
		return accounts;
	}
	public void setAccounts(List<AccountDetailsDTO> accounts) {
		this.accounts = accounts;
	}

}