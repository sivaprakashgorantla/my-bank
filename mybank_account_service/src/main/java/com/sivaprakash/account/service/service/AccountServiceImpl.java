package com.sivaprakash.account.service.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sivaprakash.account.service.dto.AccountDetailsDTO;
import com.sivaprakash.account.service.dto.CustomerProfileDTO;
import com.sivaprakash.account.service.entiry.Account;
import com.sivaprakash.account.service.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Value("${user.service.url}")
	private String userServiceUrl;

	/*
	 * @Autowired private TransactionClient transactionClient;
	 */
	public boolean isProfileComplete(Long userId) {
		System.out.println("isProfileComplete..............");
		try {

			CustomerProfileDTO profile = restTemplate.getForObject(userServiceUrl + "/api/v1/users/" + userId,
					CustomerProfileDTO.class);
			System.out.println("isProfileComplete.............." + profile);
			return profile != null && profile.isProfileComplete();
		} catch (Exception e) {
			// Log the error
			System.out.println("isProfileComplete....ERROR.........." + e.getMessage());
			throw new RuntimeException("Error checking user profile status", e);
		}
	}

	private AccountDetailsDTO convertToDTO(Account account) {
		AccountDetailsDTO dto = new AccountDetailsDTO();
		dto.setAccountId(account.getAccountId());
		dto.setAccountType(getAccountTypeName(account.getAccountType()));
		dto.setAccountNumber(account.getAccountNumber());
		dto.setBalance(account.getBalance());
		dto.setCurrencyCode(account.getCurrencyCode());
		dto.setStatus(account.getStatus().name());
		return dto;
	}

	private String getAccountTypeName(Integer accountType) {
		return switch (accountType) {
		case 1 -> "Savings Account";
		case 2 -> "Current Account";
		case 3 -> "Fixed Deposit";
		default -> "Unknown Account Type";
		};
	}

	public List<AccountDetailsDTO> getAccountsByUserCustomerId(String customerId) {
		// TODO Auto-generated method stub
		List<Account> accounts = accountRepository.findByCustomerId(customerId);
		return accounts.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public boolean updateAccountBalance(String accountNumber, BigDecimal amount, String transactionType) {
        // Fetch the account from the database
        Account account = accountRepository.findByAccountNumber(accountNumber)
            .orElseThrow(() -> new RuntimeException("Account not found: " + accountNumber));
        
        // Perform the balance update
        if (transactionType.equalsIgnoreCase("CREDIT")) {
            account.setBalance(account.getBalance().add(amount));
        } else if (transactionType.equalsIgnoreCase("DEBIT")) {
            if (account.getBalance().compareTo(amount) < 0) {
                throw new RuntimeException("Insufficient balance for account: " + accountNumber);
            }
            account.setBalance(account.getBalance().subtract(amount));
        } else {
            throw new IllegalArgumentException("Invalid transaction type: " + transactionType);
        }

        // Save the updated account
        accountRepository.save(account);
        return true;
    }
}