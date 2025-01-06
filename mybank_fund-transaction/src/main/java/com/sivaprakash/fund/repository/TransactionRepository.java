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
		    SELECT *
		    FROM (
		        SELECT t.*,
		               ROWNUM as rn
		        FROM transactions t
		        WHERE t.from_account_id = :fromAccountId OR t.to_account_id = :toAccountId
		        ORDER BY t.transaction_date DESC
		    )
		    WHERE rn <= 10
		    """, nativeQuery = true)
		List<Transaction> findTop10ByFromAccountIdOrToAccountIdOrderByTransactionDateDesc(@Param("fromAccountId") String fromAccountId, @Param("toAccountId") String toAccountId);

	/*
	 * List<Transaction>
	 * findTop10ByFromAccountIdOrToAccountIdOrderByTransactionDateDesc( String
	 * fromAccountId, String toAccountId);
	 */
    @Query("SELECT t FROM Transaction t WHERE " +
           "(t.fromAccountId = :accountId OR t.toAccountId = :accountId) AND " +
           "t.transactionDate BETWEEN :startDate AND :endDate " +
           "ORDER BY t.transactionDate DESC")
    List<Transaction> findByAccountAndDateRange(
        String accountId, LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT t FROM Transaction t WHERE " +
           "(t.fromAccountId = :accountId OR t.toAccountId = :accountId) AND " +
           "t.transactionDate >= :startDate " +
           "ORDER BY t.transactionDate DESC")
    List<Transaction> findByAccountAndDateFrom(
        String accountId, LocalDateTime startDate);

    @Query("SELECT t FROM Transaction t WHERE " +
           "(t.fromAccountId = :accountId OR t.toAccountId = :accountId) AND " +
           "t.transactionDate <= :endDate " +
           "ORDER BY t.transactionDate DESC")
    List<Transaction> findByAccountAndDateTo(
        String accountId, LocalDateTime endDate);

    List<Transaction> findByFromAccountIdOrToAccountId(
        String fromAccountId, String toAccountId);
}