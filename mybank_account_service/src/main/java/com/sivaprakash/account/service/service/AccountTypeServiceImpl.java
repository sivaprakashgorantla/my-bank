package com.sivaprakash.account.service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sivaprakash.account.service.entiry.AccountType;
import com.sivaprakash.account.service.repository.AccountTypeRepository;

@Service
public class AccountTypeServiceImpl implements AccountTypeService {

	@Autowired
	private AccountTypeRepository accountTypeRepository;

	// Fetch account type by ID
	@Override
	public Optional<AccountType> findById(Long accountTypeId) {
		return accountTypeRepository.findById(accountTypeId);
	}

	// Fetch all account types
	@Override
	public List<AccountType> findAll() {
		return accountTypeRepository.findAll();
	}

	// Save a new or existing account type
	@Override
	public AccountType save(AccountType accountType) {
		return accountTypeRepository.save(accountType);
	}

	// Delete an account type by ID
	@Override
	public void deleteById(Long accountTypeId) {
		accountTypeRepository.deleteById(accountTypeId);
	}

	// Example custom method to find account types by their name
	@Override
	public AccountType findByTypeName(String typeName) {
		Optional<AccountType> accountTypeOptional = accountTypeRepository.findByTypeName(typeName);
		AccountType accountType = null;
		if (accountTypeOptional.isPresent()) {
			accountType = accountTypeOptional.get();
			// Do something with accountType
		} else {
			// Handle the case where account type is not found
			System.out.println("Account Type not found.");
		}
		return accountType;
	}
}
