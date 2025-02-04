package com.sivaprakash.fund.data;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.sivaprakash.fund.dto.AccountDetailsDTO;
import com.sivaprakash.fund.entity.Transaction;
import com.sivaprakash.fund.repository.TransactionRepository;

@Configuration
public class TransactionDataSeeder {

	//private static final String ACCOUNT_SERVICE_URL = "http://account-service/api/v1/accounts/all";
	private static final String ACCOUNT_SERVICE_URL = "http://account-service/api/v1/accounts/all";

	@Autowired
	private RestTemplate restTemplate;

	private final SecureRandom random = new SecureRandom();
	private List<String> fetchAccountNumbers() {
		try {
			// Use ParameterizedTypeReference to correctly handle the
			// List<AccountDetailsDTO> type
			ResponseEntity<List<AccountDetailsDTO>> response = restTemplate.exchange(ACCOUNT_SERVICE_URL,
					org.springframework.http.HttpMethod.GET, null, // No request body needed for GET
					new ParameterizedTypeReference<List<AccountDetailsDTO>>() {
					});
		 	
			List<AccountDetailsDTO> accountDetails = response.getBody(); 
			if (accountDetails != null) {
				System.out.println("Fetched accounts: " + accountDetails);

				// Extract account numbers from AccountDetailsDTO
				return accountDetails.stream().map(AccountDetailsDTO::getAccountNumber).toList();
			}
		} catch (Exception e) {
			System.out.println("Error fetching accounts from the User service: " + e.getMessage());
		}

		// Return an empty list if there's an error or no accounts found
		return new ArrayList<>();
	}

	@Bean
	@Profile("!prod") // Don't run in production
	public CommandLineRunner seedTransactionData(TransactionRepository transactionRepository) {
		return args -> {
			System.out.println("Starting transaction data seeding...");

			// Fetch account numbers from the user service
			List<String> sampleAccountNumbers = fetchAccountNumbers();

			System.out.println("sampleAccountNumbers : "+sampleAccountNumbers);
			if (sampleAccountNumbers.isEmpty()) {
				System.out.println("No account numbers available for transaction seeding.");
				return;
			}

			// Create list to hold all transactions
			List<Transaction> transactions = new ArrayList<>();

			// Generate transactions for the last 30 days
			LocalDateTime now = LocalDateTime.now();
			for (int day = 0; day < 30; day++) {
				// Generate 10 transactions per day
				for (int i = 0; i < 10; i++) {
					transactions.add(createRandomTransaction(sampleAccountNumbers, now.minusDays(day)));
				}
			}

			// Save all transactions
			//transactionRepository.saveAll(transactions);

			System.out.println("Finished seeding " + transactions.size() + " transactions");
		};
	}

	private Transaction createRandomTransaction(List<String> accountNumbers, LocalDateTime dateTime) {
		Transaction transaction = new Transaction();

		// Set random from and to account numbers
		String fromAccountNumber = accountNumbers.get(random.nextInt(accountNumbers.size()));
		String toAccountNumber;
		do {
			toAccountNumber = accountNumbers.get(random.nextInt(accountNumbers.size()));
		} while (toAccountNumber.equals(fromAccountNumber)); // Ensure different accounts

		transaction.setFromAccountNumber(fromAccountNumber);
		//transaction.setToAccountNumber(toAccountNumber);

		// Set random amount between 10 and 1000
		BigDecimal amount = BigDecimal.valueOf(10 + random.nextDouble() * 990).setScale(2, BigDecimal.ROUND_HALF_UP);
		transaction.setAmount(amount);

		// Set random transaction type
		Transaction.TransactionType[] types = Transaction.TransactionType.values();
		transaction.setTransactionType(types[random.nextInt(types.length)]);

		// Set random status (mostly COMPLETED)
		if (random.nextDouble() < 0.9) {
			transaction.setStatus(Transaction.TransactionStatus.COMPLETED);
		} else {
			Transaction.TransactionStatus[] statuses = { Transaction.TransactionStatus.PENDING,
					Transaction.TransactionStatus.FAILED, Transaction.TransactionStatus.REVERSED };
			transaction.setStatus(statuses[random.nextInt(statuses.length)]);
		}

		// Set reference number
		transaction.setReferenceNumber(generateReferenceNumber());

		// Set transaction date with random time during the day
		transaction.setTransactionDate(dateTime.plusHours(random.nextInt(24)).plusMinutes(random.nextInt(60)));

		// Set description
		transaction.setDescription(generateDescription(transaction));

		// Set default currency
		transaction.setCurrencyCode("USD");

		return transaction;
	}
	
	
	private String generateReferenceNumber() {
	    return UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
	}

	private String generateDescription(Transaction transaction) {
	    return String.format("Transfer of %s %s from %s to %s", 
	        transaction.getCurrencyCode(), 
	        transaction.getAmount());
	       // transaction.getFromAccountNumber(), 
	      //  transaction.getToAccountNumber());
	}

//
//	private List<String> fetchAccountNumbers() {
//		try {
//			// Use ParameterizedTypeReference to correctly handle the
//			// List<AccountDetailsDTO> type
//			ResponseEntity<List<AccountDetailsDTO>> response = restTemplate.exchange(USER_SERVICE_URL,
//					org.springframework.http.HttpMethod.GET, null, // No request body needed for GET
//					new ParameterizedTypeReference<List<AccountDetailsDTO>>() {
//					});
//
//			List<AccountDetailsDTO> accountDetails = response.getBody();
//			if (accountDetails != null) {
//				System.out.println("Fetched accounts: " + accountDetails);
//
//				// Extract account numbers from AccountDetailsDTO
//				return accountDetails.stream().map(AccountDetailsDTO::getAccountNumber).toList();
//			}
//		} catch (Exception e) {
//			System.out.println("Error fetching accounts from the User service: " + e.getMessage());
//		}
//
//		// Return an empty list if there's an error or no accounts found
//		return new ArrayList<>();
//	}

}
