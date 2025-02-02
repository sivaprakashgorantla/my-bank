package com.sivaprakash.account.service.service;

import java.math.BigDecimal;
import java.util.List;

import com.sivaprakash.account.service.dto.AccountDetailsDTO;

public interface AccountService {

	// Create a new account
	boolean isProfileComplete(Long userId);
	
	public AccountDetailsDTO createAccount(Long customerId);

	List<AccountDetailsDTO> getAccountsDetailsByAccountNumber(String accontNumber);

	List<AccountDetailsDTO> getAccountsByUserCustomerId(Long customerId);

	boolean updateAccountBalance(String accountNumber, BigDecimal amount, String transactionType);

	List<AccountDetailsDTO> getAccounts();

}
