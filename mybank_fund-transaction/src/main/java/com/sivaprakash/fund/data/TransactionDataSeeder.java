package com.sivaprakash.fund.data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.sivaprakash.fund.entity.Transaction;
import com.sivaprakash.fund.repository.TransactionRepository;

@Configuration
public class TransactionDataSeeder {

    private final Random random = new Random();

    @Bean
    @Profile("!prod") // Don't run in production
    public CommandLineRunner seedTransactionData(TransactionRepository transactionRepository) {
        return args -> {
            System.out.println("Starting transaction data seeding...");
            
            // Create sample account IDs
            List<Long> sampleAccountIds = List.of(1001L, 1002L, 1003L, 1004L, 1005L);
            
            // Create list to hold all transactions
            List<Transaction> transactions = new ArrayList<>();
            
            // Generate transactions for the last 30 days
            LocalDateTime now = LocalDateTime.now();
            for (int day = 0; day < 30; day++) {
                // Generate 10 transactions per day
                for (int i = 0; i < 10; i++) {
                    transactions.add(createRandomTransaction(sampleAccountIds, now.minusDays(day)));
                }
            }
            
            // Save all transactions
            //transactionRepository.saveAll(transactions);
            
            System.out.println("Finished seeding " + transactions.size() + " transactions");
        };
    }

    private Transaction createRandomTransaction(List<Long> accountIds, LocalDateTime dateTime) {
        Transaction transaction = new Transaction();
        
        // Set random from and to accounts
        String fromAccount = ""+accountIds.get(random.nextInt(accountIds.size()));
        String toAccount;
        do {
            toAccount = ""+accountIds.get(random.nextInt(accountIds.size()));
        } while (toAccount.equals(fromAccount)); // Ensure different accounts
        
        transaction.setFromAccountId(fromAccount);
        transaction.setToAccountId(toAccount);
        
        // Set random amount between 10 and 1000
        BigDecimal amount = BigDecimal.valueOf(10 + random.nextDouble() * 990)
            .setScale(2, BigDecimal.ROUND_HALF_UP);
        transaction.setAmount(amount);
        
        // Set random transaction type
        Transaction.TransactionType[] types = Transaction.TransactionType.values();
        transaction.setTransactionType(types[random.nextInt(types.length)]);
        
        // Set random status (mostly COMPLETED)
        if (random.nextDouble() < 0.9) {
            transaction.setStatus(Transaction.TransactionStatus.COMPLETED);
        } else {
            Transaction.TransactionStatus[] statuses = {
                Transaction.TransactionStatus.PENDING,
                Transaction.TransactionStatus.FAILED,
                Transaction.TransactionStatus.REVERSED
            };
            transaction.setStatus(statuses[random.nextInt(statuses.length)]);
        }
        
        // Set reference number
        transaction.setReferenceNumber(generateReferenceNumber());
        
        // Set transaction date with random time during the day
        transaction.setTransactionDate(dateTime.plusHours(random.nextInt(24))
            .plusMinutes(random.nextInt(60)));
        
        // Set description
        transaction.setDescription(generateDescription(transaction));
        
        // Set default currency
        transaction.setCurrencyCode("USD");
        
        return transaction;
    }

    private String generateReferenceNumber() {
        return "TXN" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private String generateDescription(Transaction transaction) {
        String baseDescription = switch (transaction.getTransactionType()) {
            case TRANSFER -> "Fund transfer";
            case DEPOSIT -> "Cash deposit";
            case WITHDRAWAL -> "Cash withdrawal";
        };
        
        return String.format("%s - %s to %s", 
            baseDescription, 
            transaction.getFromAccountId(), 
            transaction.getToAccountId());
    }
}