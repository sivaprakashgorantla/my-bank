package com.sivaprakash.beneficiary.dataloader;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.sivaprakash.beneficiary.entity.Beneficiary;
import com.sivaprakash.beneficiary.entity.Beneficiary.BeneficiaryStatus;
import com.sivaprakash.beneficiary.entity.Beneficiary.BeneficiaryType;
import com.sivaprakash.beneficiary.repository.BeneficiaryRepository;

@Configuration
@Order(1)
public class BeneficiaryDataInitializer {

    @Bean
    CommandLineRunner initBeneficiaries(BeneficiaryRepository beneficiaryRepository) {
        return args -> {
            // Check if data already exists
            if (beneficiaryRepository.count() > 0) {
                System.out.println("Beneficiaries data already exists. Skipping initialization.");
                return;
            }

            LocalDateTime now = LocalDateTime.now();

            // Create default beneficiaries
            Beneficiary[] beneficiaries = new Beneficiary[] {
                createBeneficiary(
                    86L, "ACC0987654321", "BANK001", 
                    "John Doe", "Bank A", "john.doe@example.com",
                    BeneficiaryType.INTERNAL, "Friend", 5000.00,
                    now
                ),
                createBeneficiary(
                    86L, "ACC1122334455", "BANK002", 
                    "Jane Smith", "Bank B", "jane.smith@example.com",
                    BeneficiaryType.EXTERNAL, "Colleague", 10000.00,
                    now
                ),
                createBeneficiary(
                    86L, "ACC1234567890", "BANK003", 
                    "Alice Johnson", "Bank C", "alice.johnson@example.com",
                    BeneficiaryType.INTERNAL, "Sister", 20000.00,
                    now
                ),
                createBeneficiary(
                    86L, "ACC1234567891", "BANK003", 
                    "Bob Williams", "Bank C", "bob.williams@example.com",
                    BeneficiaryType.EXTERNAL, "Brother", 15000.00,
                    now
                ),
                createBeneficiary(
                    86L, "ACC1234567892", "BANK004", 
                    "Charlie Brown", "Bank D", "charlie.brown@example.com",
                    BeneficiaryType.INTERNAL, "Friend", 8000.00,
                    now
                ),
                createBeneficiary(
                    86L, "ACC1234567893", "BANK005", 
                    "Emily Davis", "Bank E", "emily.davis@example.com",
                    BeneficiaryType.EXTERNAL, "Cousin", 7000.00,
                    now
                )
            };

            try {
                // Save beneficiaries to the repository
                beneficiaryRepository.saveAll(Arrays.asList(beneficiaries));
                System.out.println("Successfully initialized default beneficiaries.");
            } catch (Exception e) {
                System.err.println("Error initializing beneficiaries: " + e.getMessage());
                throw e;
            }
        };
    }

    // Define the method to create a Beneficiary object
    private Beneficiary createBeneficiary(
            Long constumerId, 
            String accountNumber, 
            String bankCode,
            String name,
            String bankName,
            String email,
            BeneficiaryType type,
            String relationship,
            double transferLimit,
            LocalDateTime timestamp) {

        return new Beneficiary(
        		constumerId, // Pass the actual userId here
            accountNumber, // Pass the actual account number here
            bankCode, // Pass the actual bank code here
            name,
            bankName,
            email,
            type,
            relationship,
            BeneficiaryStatus.ACTIVE,
            BigDecimal.valueOf(transferLimit),
            timestamp,
            timestamp,
            null
        );
    }
}
