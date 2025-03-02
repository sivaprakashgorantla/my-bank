package com.sivaprakash.loan.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sivaprakash.loan.entity.LoanApplication;
import com.sivaprakash.loan.enums.LoanStatus;

@Repository
public interface LoanRepository extends JpaRepository<LoanApplication, Long> {
    List<LoanApplication> findByCustomerIdOrderByApplicationDateDesc(Long customerId);
    List<LoanApplication> findByStatus(LoanStatus status);
    
    @Query("SELECT l FROM LoanApplication l WHERE l.customerId = :customerId AND l.status IN :statuses")
    List<LoanApplication> findByCustomerIdAndStatusIn(Long customerId, List<LoanStatus> statuses);
    
    @Query("SELECT COUNT(l) FROM LoanApplication l WHERE l.customerId = :customerId AND l.status = 'ACTIVE'")
    long countActiveLoans(Long customerId);
    
    @Modifying
    @Query("UPDATE LoanApplication l SET l.status = :status WHERE l.loanId = :loanId")
    int updateLoanStatus(@Param("loanId") Long loanId, @Param("status") LoanStatus status);
    
    @Query("SELECT l FROM LoanApplication l WHERE l.nextPaymentDate <= :date AND l.status = 'ACTIVE'")
    List<LoanApplication> findLoansWithUpcomingPayments(@Param("date") LocalDateTime date);
    
    @Query("SELECT l FROM LoanApplication l WHERE l.customerId = :customerId AND l.status != :status")
    List<LoanApplication> findByCustomerIdAndStatusNot(@Param("customerId") Long customerId, @Param("status") LoanStatus status);

    @Modifying
    @Query("UPDATE LoanApplication l SET l.processingFeePaid = true WHERE l.loanId = :loanId")
    int payProcessingFee(@Param("loanId") Long loanId);

    
    Optional<LoanApplication> findByReferenceNumber(String referenceNumber);

}