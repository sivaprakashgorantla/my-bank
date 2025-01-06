package com.sivaprakash.loan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sivaprakash.loan.LoanApplicationRequest;
import com.sivaprakash.loan.LoanUpdateRequest;
import com.sivaprakash.loan.ProcessingFeePaymentRequest;
import com.sivaprakash.loan.entity.LoanApplication;
import com.sivaprakash.loan.response.LoanApplicationResponse;
import com.sivaprakash.loan.response.PaymentResponse;
import com.sivaprakash.loan.service.LoanService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    // Apply for a new loan
    @PostMapping("/apply")
    public ResponseEntity<LoanApplicationResponse> applyForLoan(@Valid @RequestBody LoanApplicationRequest request) {
        return ResponseEntity.ok(loanService.createLoanApplication(request));
    }

    // Get loan details by ID
    @GetMapping("/{loanId}")
    public ResponseEntity<LoanApplication> getLoanDetails(@PathVariable Long loanId) {
        return ResponseEntity.ok(loanService.getLoanApplication(loanId));
    }

    // Update an existing loan application
    @PutMapping("/{loanId}")
    public ResponseEntity<LoanApplication> updateLoanApplication(
            @PathVariable Long loanId,
            @Valid @RequestBody LoanUpdateRequest request) {
        return ResponseEntity.ok(loanService.updateLoanApplication(loanId, request));
    }

    // Cancel a loan application
    @DeleteMapping("/{loanId}")
    public ResponseEntity<Void> cancelLoanApplication(@PathVariable Long loanId) {
        loanService.cancelLoanApplication(loanId);
        return ResponseEntity.noContent().build();
    }

    // Pay the processing fee for a loan application
    @PostMapping("/{loanId}/processing-fee")
    public ResponseEntity<PaymentResponse> payProcessingFee(
            @PathVariable Long loanId,
            @Valid @RequestBody ProcessingFeePaymentRequest request) {
        return ResponseEntity.ok(loanService.processProcessingFeePayment(loanId, request));
    }

    // Get all active loans for a user
    @GetMapping("/user/{userId}/active")
    public ResponseEntity<List<LoanApplication>> getActiveLoansForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(loanService.getActiveLoansForUser(userId));
    }

    // Get loan status by reference number
    @GetMapping("/status/{referenceNumber}")
    public ResponseEntity<LoanApplicationResponse> getLoanStatus(@PathVariable String referenceNumber) {
        return ResponseEntity.ok(loanService.getLoanStatus(referenceNumber));
    }
}
