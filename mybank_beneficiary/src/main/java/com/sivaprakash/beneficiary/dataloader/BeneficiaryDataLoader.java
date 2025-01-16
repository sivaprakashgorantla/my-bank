package com.sivaprakash.beneficiary.dataloader;

import com.sivaprakash.beneficiary.entity.Beneficiary;
import com.sivaprakash.beneficiary.entity.Beneficiary.BeneficiaryStatus;
import com.sivaprakash.beneficiary.entity.Beneficiary.BeneficiaryType;
import com.sivaprakash.beneficiary.repository.BeneficiaryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class BeneficiaryDataLoader {

    @Bean
    CommandLineRunner loadData(BeneficiaryRepository beneficiaryRepository) {
        return args -> {
            List<Beneficiary> beneficiaries = List.of(
                    new Beneficiary(null, 86L, "John Doe", "ACC0987654321", "BANK001", "Bank A", "johndoe@example.com", BeneficiaryType.INTERNAL, "Friend", BeneficiaryStatus.ACTIVE, new BigDecimal("10000.00"), LocalDateTime.now(), LocalDateTime.now(), null),
                    new Beneficiary(null, 81L, "Jane Smith", "ACC1122334455", "BANK002", "Bank B", "janesmith@example.com", BeneficiaryType.EXTERNAL, "Colleague", BeneficiaryStatus.ACTIVE, new BigDecimal("15000.00"), LocalDateTime.now(), LocalDateTime.now(), null),
                    new Beneficiary(null, 82L, "Alice Brown", "ACC1234567890", "BANK003", "Bank C", "alicebrown@example.com", BeneficiaryType.INTERNAL, "Sister", BeneficiaryStatus.INACTIVE, new BigDecimal("5000.00"), LocalDateTime.now(), LocalDateTime.now(), null),
                    new Beneficiary(null, 83L, "Bob Green", "ACC1234567891", "BANK004", "Bank D", "bobgreen@example.com", BeneficiaryType.EXTERNAL, "Brother", BeneficiaryStatus.SUSPENDED, new BigDecimal("2000.00"), LocalDateTime.now(), LocalDateTime.now(), null),
                    new Beneficiary(null, 84L, "Charlie Black", "ACC1234567892", "BANK005", "Bank E", "charlieblack@example.com", BeneficiaryType.INTERNAL, "Uncle", BeneficiaryStatus.ACTIVE, new BigDecimal("3000.00"), LocalDateTime.now(), LocalDateTime.now(), null),
                    new Beneficiary(null, 85L, "Diana White", "ACC1234567893", "BANK006", "Bank F", "dianawhite@example.com", BeneficiaryType.EXTERNAL, "Aunt", BeneficiaryStatus.ACTIVE, new BigDecimal("4000.00"), LocalDateTime.now(), LocalDateTime.now(), null)
            );

//            beneficiaryRepository.saveAll(beneficiaries);
        };
    }
}
