package com.sivaprakash.account.service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sivaprakash.account.service.entiry.Account;
import com.sivaprakash.account.service.entiry.AccountType;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	List<Account> findByCustomerId(Long customerId);

	List<Account> findByaccountNumber(String accountNumber);

	Optional<Account> findByAccountNumber(String accountNumber); // Use Optional for null safety

	// Custom query for checking account number existence for Oracle compatibility
	@Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Account a WHERE a.accountNumber = ?1")
	boolean existsByAccountNumber(String accountNumber);

	Optional<Account> findByAccountType(AccountType accountType);

}