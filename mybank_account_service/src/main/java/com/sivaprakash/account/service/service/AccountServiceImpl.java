package com.sivaprakash.account.service.service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sivaprakash.account.service.dto.AccountDetailsDTO;
import com.sivaprakash.account.service.dto.CustomerProfileDTO;
import com.sivaprakash.account.service.entiry.Account;
import com.sivaprakash.account.service.entiry.AccountType;
import com.sivaprakash.account.service.enums.AccountStatus;
import com.sivaprakash.account.service.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

	private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AccountTypeService accountTypeService;
	@Autowired
	private RestTemplate restTemplate;

	@Value("${user.service.url}")
	private String userServiceUrl;

	/*
	 * @Autowired private TransactionClient transactionClient;
	 */

	@Override
	public AccountDetailsDTO createAccount(Long customerId) {
		logger.info("Creating a new account with details: {}", customerId);

		AccountType accountType = accountTypeService.findByTypeName(customerId %2 == 0 ? "CURRENT" : "SAVING");

		// Convert AccountDetailsDTO to Account entity
		Account account = new Account();
		account.setAccountNumber(generateAccountNumber()); // You should have a method to generate a unique account
															// number
		account.setAccountType(accountType);
		account.setBalance(generageRamdomValue());
		account.setCurrencyCode("INR");
		account.setStatus(AccountStatus.ACTIVE); // Set the default status as active
		account.setCustomerId(customerId);
		account.setCreatedAt(LocalDateTime.now());
		// Save the account to the database
		Account savedAccount = accountRepository.save(account);

		// Convert the saved account back to DTO
		return convertToDTO(savedAccount);
	}

	private BigDecimal generageRamdomValue() {
		// TODO Auto-generated method stub
		SecureRandom random = new SecureRandom();
        BigDecimal min = new BigDecimal("5000");
        BigDecimal max = new BigDecimal("10000");

        // Generate a random double in range 0.0 to 1.0
        BigDecimal randomFactor = new BigDecimal(random.nextDouble());

        // Scale to the desired range
        BigDecimal randomValue = min.add(randomFactor.multiply(max.subtract(min)));

        // Round to 2 decimal places (optional)
        randomValue = randomValue.setScale(2, BigDecimal.ROUND_HALF_UP);

        System.out.println("Random BigDecimal Value: " + randomValue);
        return randomValue;
	}

	private String generateAccountNumber() {
		// Logic to generate a unique account number, could be based on a sequence or
		// random generation
		return "ACC" + System.currentTimeMillis(); // Example
	}

	public boolean isProfileComplete(Long userId) {
		logger.info("Checking if profile is complete for userId: {}", userId);
		try {
			CustomerProfileDTO profile = restTemplate.getForObject(userServiceUrl + "/api/v1/users/" + userId,
					CustomerProfileDTO.class);
			logger.debug("Profile details for userId {}: {}", userId, profile);
			return profile != null && profile.isProfileComplete();
		} catch (Exception e) {
			logger.error("Error checking user profile status for userId {}: {}", userId, e.getMessage(), e);
			throw new RuntimeException("Error checking user profile status", e);
		}
	}

	private AccountDetailsDTO convertToDTO(Account account) {
		logger.debug("Converting Account entity to DTO for accountId: {}", account.getAccountId());
		AccountDetailsDTO dto = new AccountDetailsDTO();
		dto.setAccountId(account.getAccountId());
		dto.setAccountType(getAccountTypeName(account.getAccountType().getAccountTypeId()));
		dto.setAccountNumber(account.getAccountNumber());
		dto.setBalance(account.getBalance());
		dto.setCurrencyCode(account.getCurrencyCode());
		dto.setStatus(account.getStatus().name());
		return dto;
	}

	private String getAccountTypeName(Long accountType) {
		if (accountType == null) {
			return "Unknown Account Type";
		}

		return switch (accountType.intValue()) { // Cast Long to int
		case 1 -> "Savings Account";
		case 2 -> "Current Account";
		case 3 -> "Fixed Deposit";
		default -> "Unknown Account Type";
		};
	}

	public List<AccountDetailsDTO> getAccountsByUserCustomerId(Long customerId) {
		logger.info("Fetching accounts for customerId: {}", customerId);
		List<Account> accounts = accountRepository.findByCustomerId(customerId);
		logger.debug("Accounts retrieved for customerId {}: {}", customerId, accounts);
		return accounts.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	public List<AccountDetailsDTO> getAccountsDetailsByAccountNumber(String accountNumber) {
		logger.info("Fetching account details for accountNumber: {}", accountNumber);
		Optional<Account> account = accountRepository.findByAccountNumber(accountNumber);
		if (account.isEmpty()) {
			logger.warn("No account found for accountNumber: {}", accountNumber);
		}
		return account.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public boolean updateAccountBalance(String accountNumber, BigDecimal amount, String transactionType) {
		logger.info("Updating account balance for accountNumber: {}, amount: {}, transactionType: {}", accountNumber,
				amount, transactionType);

		// Fetch the account from the database
		Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> {
			logger.error("Account not found: {}", accountNumber);
			return new RuntimeException("Account not found: " + accountNumber);
		});

		// Perform the balance update
		if (transactionType.equalsIgnoreCase("CREDIT")) {
			logger.debug("Crediting amount {} to account {}", amount, accountNumber);
			account.setBalance(account.getBalance().add(amount));
		} else if (transactionType.equalsIgnoreCase("DEBIT")) {
			if (account.getBalance().compareTo(amount) < 0) {
				logger.error("Insufficient balance for account: {}", accountNumber);
				throw new RuntimeException("Insufficient balance for account: " + accountNumber);
			}
			logger.debug("Debiting amount {} from account {}", amount, accountNumber);
			account.setBalance(account.getBalance().subtract(amount));
		} else {
			logger.error("Invalid transaction type: {}", transactionType);
			throw new IllegalArgumentException("Invalid transaction type: " + transactionType);
		}

		// Save the updated account
		accountRepository.save(account);
		logger.info("Account balance updated successfully for accountNumber: {}", accountNumber);
		return true;
	}

	@Override
	public List<AccountDetailsDTO> getAccounts() {
		logger.info("Fetching all accounts from the database.");

		try {
			List<Account> accounts = accountRepository.findAll();
			logger.debug("Accounts retrieved: {}", accounts);

			return accounts.stream().map(this::convertToDTO).collect(Collectors.toList());
		} catch (Exception e) {
			logger.error("Error occurred while fetching accounts: {}", e.getMessage(), e);
			throw new RuntimeException("Error fetching accounts.", e);
		}
	}

}