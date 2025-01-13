package com.sivaprakash.account.service.service;

import java.util.List;

import com.sivaprakash.account.service.dto.AccountDetailsDTO;

public interface AccountService {

	// Create a new account
	boolean isProfileComplete(Long userId);

	List<AccountDetailsDTO> getAccountsByUserCustomerId(String customerId);

}
