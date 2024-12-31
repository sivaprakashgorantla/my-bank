package com.sivaprakash.loan.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sivaprakash.loan.entity.LoanPayment;
import com.sivaprakash.loan.enums.PaymentStatus;

// PaymentRepository.java
@Repository
public interface PaymentRepository extends JpaRepository<LoanPayment, Long> {
    List<LoanPayment> findByLoanIdOrderByPaymentDateDesc(Long loanId);
    
    @Query("SELECT p FROM LoanPayment p WHERE p.loanId = :loanId AND p.status = :status")
    List<LoanPayment> findByLoanIdAndStatus(Long loanId, PaymentStatus status);
    
    @Query("SELECT SUM(p.amount) FROM LoanPayment p WHERE p.loanId = :loanId AND p.status = 'COMPLETED'")
    BigDecimal getTotalPaidAmount(@Param("loanId") Long loanId);
    
    @Query("SELECT p FROM LoanPayment p WHERE p.status = 'PENDING' AND p.paymentDate <= :date")
    List<LoanPayment> findPendingPayments(@Param("date") LocalDateTime date);
    
    @Modifying
    @Query("UPDATE LoanPayment p SET p.status = :status WHERE p.paymentId = :paymentId")
    int updatePaymentStatus(@Param("paymentId") Long paymentId, @Param("status") PaymentStatus status);
    
    @Query("SELECT COUNT(p) FROM LoanPayment p WHERE p.loanId = :loanId AND p.status = 'FAILED'")
    long countFailedPayments(@Param("loanId") Long loanId);
}

// LoanApplicationRequest.java
