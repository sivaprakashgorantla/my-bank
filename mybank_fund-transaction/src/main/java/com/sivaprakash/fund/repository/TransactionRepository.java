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

    // Native Query: Use database column names
    @Query(value = """
            SELECT *
            FROM (
                SELECT t.*,
                       ROWNUM as rn
                FROM transactions t
                WHERE t.from_account_number = :fromAccountNumber OR t.to_account_number = :toAccountNumber
                ORDER BY t.transaction_date DESC
            )
            WHERE rn <= 10
            """, nativeQuery = true)
    List<Transaction> findTop10ByFromAccountIdOrToAccountIdOrderByTransactionDateDesc(
            @Param("fromAccountNumber") String fromAccountNumber,
            @Param("toAccountNumber") String toAccountNumber);

    // JPQL Query: Use entity field names
    @Query("SELECT t FROM Transaction t WHERE " +
           "(t.fromAccountNumber = :accountNumber OR t.toAccountNumber = :accountNumber) AND " +
           "t.transactionDate BETWEEN :startDate AND :endDate " +
           "ORDER BY t.transactionDate DESC")
    List<Transaction> findByAccountAndDateRange(
            @Param("accountNumber") String accountNumber,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT t FROM Transaction t WHERE " +
           "(t.fromAccountNumber = :accountNumber OR t.toAccountNumber = :accountNumber) AND " +
           "t.transactionDate >= :startDate " +
           "ORDER BY t.transactionDate DESC")
    List<Transaction> findByAccountAndDateFrom(
            @Param("accountNumber") String accountNumber,
            @Param("startDate") LocalDateTime startDate);

    @Query("SELECT t FROM Transaction t WHERE " +
           "(t.fromAccountNumber = :accountNumber OR t.toAccountNumber = :accountNumber) AND " +
           "t.transactionDate <= :endDate " +
           "ORDER BY t.transactionDate DESC")
    List<Transaction> findByAccountAndDateTo(
            @Param("accountNumber") String accountNumber,
            @Param("endDate") LocalDateTime endDate);

    // Spring Data JPA Derived Query: Use entity field names
    List<Transaction> findByFromAccountNumberOrToAccountNumber(
            String fromAccountNumber, String toAccountNumber);
}
