package com.sivaprakash.fund.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sivaprakash.fund.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	@Query(value = """
		    SELECT * FROM (
		        SELECT t.* FROM transactions t 
		        WHERE t.account_number = :accountNumber 
		        ORDER BY t.transaction_date DESC
		    ) WHERE ROWNUM <= 10
		    """, nativeQuery = true)
		List<Transaction> findLast10Transactions(@Param("accountNumber") String accountNumber);
	
	
	//    List<Transaction> findTop10ByFromAccountIdOrToAccountIdOrderByTransactionDateDesc(
//            @Param("fromAccountNumber") String fromAccountNumber,
//            @Param("toAccountNumber") String toAccountNumber);
//
//    // JPQL Query: Use entity field names
//    @Query("SELECT t FROM Transaction t WHERE " +
//           "(t.fromAccountNumber = :accountNumber OR t.toAccountNumber = :accountNumber) AND " +
//           "t.transactionDate BETWEEN :startDate AND :endDate " +
//           "ORDER BY t.transactionDate DESC")
//    List<Transaction> findByAccountAndDateRange(
//            @Param("accountNumber") String accountNumber,
//            @Param("startDate") LocalDateTime startDate,
//            @Param("endDate") LocalDateTime endDate);
//
//    @Query("SELECT t FROM Transaction t WHERE " +
//           "(t.fromAccountNumber = :accountNumber OR t.toAccountNumber = :accountNumber) AND " +
//           "t.transactionDate >= :startDate " +
//           "ORDER BY t.transactionDate DESC")
//    List<Transaction> findByAccountAndDateFrom(
//            @Param("accountNumber") String accountNumber,
//            @Param("startDate") LocalDateTime startDate);
//
//    @Query("SELECT t FROM Transaction t WHERE " +
//           "(t.fromAccountNumber = :accountNumber OR t.toAccountNumber = :accountNumber) AND " +
//           "t.transactionDate <= :endDate " +
//           "ORDER BY t.transactionDate DESC")
//    List<Transaction> findByAccountAndDateTo(
//            @Param("accountNumber") String accountNumber,
//            @Param("endDate") LocalDateTime endDate);
//
//    // Spring Data JPA Derived Query: Use entity field names
//    List<Transaction> findByFromAccountNumberOrToAccountNumber(
//            String fromAccountNumber, String toAccountNumber);
}
