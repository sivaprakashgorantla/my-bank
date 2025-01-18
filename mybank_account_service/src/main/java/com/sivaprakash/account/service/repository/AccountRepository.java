package com.sivaprakash.account.service.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sivaprakash.account.service.entiry.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByCustomerId(String customerId);

    Optional<Account> findByAccountNumber(String accountNumber); // Use Optional for null safety
}