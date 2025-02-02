package com.sivaprakash.account.service.runner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.sivaprakash.account.service.dto.CustomerDTO;
import com.sivaprakash.account.service.entiry.Account;
import com.sivaprakash.account.service.entiry.AccountNumberGenerator;
import com.sivaprakash.account.service.entiry.AccountType;
import com.sivaprakash.account.service.enums.AccountStatus;
import com.sivaprakash.account.service.repository.AccountRepository;
import com.sivaprakash.account.service.repository.AccountTypeRepository;

@Configuration
public class DataInitializer {

	@Autowired
	private RestTemplate restTemplate;
	
    private static final String USER_SERVICE_URL = "http://USER-SERVICE/api/v1/customers/all"; // Replace with actual URL of your User Service
    @Bean
    public CommandLineRunner insertDefaultAccounts(AccountRepository accountRepository,AccountTypeRepository accountTypeRepository) {
        return args -> {
            System.out.println("Inserting default account data...");

            
            // Fetch customer IDs from the User service
            List<CustomerDTO> customerIds = fetchCustomers();

            if (customerIds == null || customerIds.isEmpty()) {
                System.out.println("No customer data found.");
                return;
            }

            // Loop through each customerId and create accounts dynamically
            for (CustomerDTO customerDTO: customerIds) {
            	System.out.println("customerDTO.getCustomerId() : "+customerDTO.getCustomerId());
                createAccountForCustomer(accountRepository,accountTypeRepository, customerDTO.getCustomerId());
            }

            System.out.println("Default account data inserted successfully.");
        };
    }
    private void createAccountForCustomer(AccountRepository accountRepository, AccountTypeRepository accountTypeRepository, Long customerId) {
        // Fetch all AccountType instances from the database
        List<AccountType> allAccountTypes = createDummyAccountTypes(accountTypeRepository);

        // Handle case where account types are not found
        if (allAccountTypes.isEmpty()) {
            throw new RuntimeException("Account types not found");
        }

        // List to hold all the accounts to be saved
        List<Account> accounts = new ArrayList<>();

        // Loop through each AccountType to create multiple accounts
        for (AccountType accountType : allAccountTypes) {
            // Create multiple accounts per AccountType (e.g., 3 accounts per type)
            for (int i = 0; i < 10; i++) { // You can change 3 to any number of accounts you want to create
                Account account = new Account(
                    null, 
                    customerId, 
                    accountType, // Set AccountType entity dynamically
                    AccountNumberGenerator.generateAccountNumber(), 
                    new BigDecimal(1000 * (i + 1)), // Example balance increasing by 1000 per account
                    "USD", // You can customize the currency
                    LocalDateTime.now(), 
                    LocalDateTime.now(), 
                    (i % 2 == 0) ? AccountStatus.ACTIVE : AccountStatus.INACTIVE // Example of alternating statuses
                );
                
                // Add the newly created account to the accounts list
                accounts.add(account);
            }
        }

        // Save all created accounts to the database
        //accountRepository.saveAll(accounts);
    }

   
    private List<CustomerDTO> fetchCustomers() {
        try {
            // Use ParameterizedTypeReference to correctly handle the List<CustomerDTO> type
            ResponseEntity<List<CustomerDTO>> response = restTemplate.exchange(
                    USER_SERVICE_URL,
                    org.springframework.http.HttpMethod.GET,
                    null, // No request body needed for GET
                    new ParameterizedTypeReference<List<CustomerDTO>>() {}
            );

            List<CustomerDTO> customers = response.getBody();
            System.out.println("Fetched customers: " + customers);
            return customers; // Return the list of CustomerDTOs
        } catch (Exception e) {
            System.out.println("Error fetching customers from the User service: " + e.getMessage());
            return null;
        }
    }
    
    public List<AccountType> createDummyAccountTypes(AccountTypeRepository accountTypeRepository) {
        // Create dummy AccountType objects
        AccountType checkingAccount = new AccountType();
        checkingAccount.setAccountTypeId(1L);
        checkingAccount.setTypeName("CURRENT");
        checkingAccount.setDescription("Standard checking account with no interest");
        checkingAccount.setInterestRate(new BigDecimal("0.00"));
        checkingAccount.setMaxWithdrawalLimit(new BigDecimal("5000.00"));
        checkingAccount.setMinBalance(new BigDecimal("100.00"));

        AccountType savingsAccount = new AccountType();
        savingsAccount.setAccountTypeId(2L);
        savingsAccount.setTypeName("SAVING");
        savingsAccount.setDescription("Savings account with interest");
        savingsAccount.setInterestRate(new BigDecimal("2.50"));
        savingsAccount.setMaxWithdrawalLimit(new BigDecimal("2000.00"));
        savingsAccount.setMinBalance(new BigDecimal("200.00"));

        AccountType businessAccount = new AccountType();
        businessAccount.setAccountTypeId(3L);
        businessAccount.setTypeName("BUSINESS");
        businessAccount.setDescription("Business account with higher limits");
        businessAccount.setInterestRate(new BigDecimal("1.00"));
        businessAccount.setMaxWithdrawalLimit(new BigDecimal("10000.00"));
        businessAccount.setMinBalance(new BigDecimal("500.00"));

        // Save to database
//        accountTypeRepository.save(checkingAccount);
//        accountTypeRepository.save(savingsAccount);
//        accountTypeRepository.save(businessAccount);

        System.out.println("Dummy account types created successfully.");
        
        return accountTypeRepository.findAll();
    }
}
