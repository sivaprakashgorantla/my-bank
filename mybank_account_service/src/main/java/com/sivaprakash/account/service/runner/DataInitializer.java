package com.sivaprakash.account.service.runner;

import com.sivaprakash.account.service.entiry.Account;
import com.sivaprakash.account.service.entiry.Account.AccountStatus;
import com.sivaprakash.account.service.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner insertDefaultAccounts(AccountRepository accountRepository) {
        return args -> {
            System.out.println("Inserting default account data...");

            // Create default accounts
            Account account1 = new Account(null,1L,1,"ACC1234567890",new BigDecimal("1000.00"),"USD",LocalDateTime.now(),LocalDateTime.now(),AccountStatus.ACTIVE);

            Account account2 = new Account(null,2L,2,"ACC0987654321",new BigDecimal("2000.00"),"USD",LocalDateTime.now(),LocalDateTime.now(),AccountStatus.INACTIVE);

           Account account3 = new Account(null,3L,1,"ACC1122334455",new BigDecimal("500.00"),"USD",LocalDateTime.now(),LocalDateTime.now(),AccountStatus.FROZEN);

            Account account4 = new Account(null,1L,1,"ACC1234567891",new BigDecimal("100000.00"),"USD",LocalDateTime.now(),LocalDateTime.now(),AccountStatus.ACTIVE);

            Account account5 = new Account(null,2L,2,"ACC1234567892",new BigDecimal("200000.00"),"USD",LocalDateTime.now(),LocalDateTime.now(),AccountStatus.INACTIVE);

            Account account6 = new Account(null,3L,1,"ACC1234567893",new BigDecimal("500000.00"),"USD",LocalDateTime.now(),LocalDateTime.now(),AccountStatus.FROZEN);
            
            // Save the accounts to the database
//            accountRepository.saveAll(Arrays.asList(account1, account2, account3,account4, account5, account6));
            System.out.println("Default account data inserted successfully.");
        };
    }
}
