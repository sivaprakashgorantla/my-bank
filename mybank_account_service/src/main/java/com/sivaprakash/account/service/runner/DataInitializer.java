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
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.sivaprakash.account.service.dto.CustomerDTO;
import com.sivaprakash.account.service.entiry.Account;
import com.sivaprakash.account.service.entiry.AccountNumberGenerator;
import com.sivaprakash.account.service.entiry.Account.AccountStatus;
import com.sivaprakash.account.service.repository.AccountRepository;

import jakarta.ws.rs.HttpMethod;

@Configuration
public class DataInitializer {

	@Autowired
	private RestTemplate restTemplate;
	
    private static final String USER_SERVICE_URL = "http://USER-SERVICE/api/v1/customers/all"; // Replace with actual URL of your User Service
    @Bean
    public CommandLineRunner insertDefaultAccounts(AccountRepository accountRepository) {
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
                createAccountForCustomer(accountRepository, customerDTO.getCustomerId());
            }

            System.out.println("Default account data inserted successfully.");
        };
    }

    private void createAccountForCustomer(AccountRepository accountRepository, Long customerId) {
        // Create default accounts for each customer

        // You can create multiple account types here; using accountType = 1 as an example
        Account account1 = new Account(null, customerId.toString(), 1, AccountNumberGenerator.generateAccountNumber(), new BigDecimal("1000.00"), "USD",
                LocalDateTime.now(), LocalDateTime.now(), AccountStatus.ACTIVE);

        
        Account account2 = new Account(null, customerId.toString(), 2, AccountNumberGenerator.generateAccountNumber(), new BigDecimal("2000.00"), "USD",
                LocalDateTime.now(), LocalDateTime.now(), AccountStatus.INACTIVE);

        Account account3 = new Account(null, customerId.toString(), 1, AccountNumberGenerator.generateAccountNumber(), new BigDecimal("500.00"), "USD",
                LocalDateTime.now(), LocalDateTime.now(), AccountStatus.FROZEN);

        Account account4 = new Account(null, customerId.toString(), 1, AccountNumberGenerator.generateAccountNumber(), new BigDecimal("100000.00"), "USD",
                LocalDateTime.now(), LocalDateTime.now(), AccountStatus.ACTIVE);

        
        Account account5 = new Account(null, customerId.toString(), 2, AccountNumberGenerator.generateAccountNumber(), new BigDecimal("200000.00"), "USD",
                LocalDateTime.now(), LocalDateTime.now(), AccountStatus.INACTIVE);

        Account account6 = new Account(null, customerId.toString(), 1, AccountNumberGenerator.generateAccountNumber(), new BigDecimal("500000.00"), "USD",
                LocalDateTime.now(), LocalDateTime.now(), AccountStatus.FROZEN);

        
        
        // Save the accounts to the database for the current customer
//        accountRepository.saveAll(List.of(account1, account2, account3, account4, account5, account6));
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
}
