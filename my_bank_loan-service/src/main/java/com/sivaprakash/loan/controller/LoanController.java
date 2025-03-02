package com.sivaprakash.loan.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.sivaprakash.loan.request.LoanApplicationRequest;
import com.sivaprakash.loan.request.LoanUpdateRequest;
import com.sivaprakash.loan.request.ProcessingFeePaymentRequest;
import com.sivaprakash.loan.entity.LoanApplication;
import com.sivaprakash.loan.response.LoanApplicationResponse;
import com.sivaprakash.loan.response.PaymentResponse;
import com.sivaprakash.loan.service.LoanService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {

    private static final Logger logger = LoggerFactory.getLogger(LoanController.class);

    @Autowired
    private LoanService loanService;

    
    @GetMapping()
    public String wish () {
    	return "Loan aplication !!";
    }
    // Apply for a new loan
    @PostMapping("/apply")
    public ResponseEntity<LoanApplicationResponse> applyForLoan(@Valid @RequestBody LoanApplicationRequest request) {
        logger.info("Received loan application request: {}", request);
        LoanApplicationResponse response = loanService.createLoanApplication(request);
        logger.info("Loan application created successfully with reference number: {}", response.getReferenceNumber());
        return ResponseEntity.ok(response);
    }

    // Get loan details by ID
    @GetMapping("/{loanId}")
    public ResponseEntity<LoanApplication> getLoanDetails(@PathVariable Long loanId) {
        logger.info("Fetching loan details for loanId: {}", loanId);
        LoanApplication loanApplication = loanService.getLoanApplication(loanId);
        logger.info("Fetched loan details for loanId: {}", loanId);
        return ResponseEntity.ok(loanApplication);
    }

    @GetMapping("/allApproval")
    public ResponseEntity<List<LoanApplication>> allApprovals() {
        logger.info("Fetching allApprovals for loanId: {}");
        List<LoanApplication> loanApplication = loanService.allApprovals();
        logger.info("Fetched loan details for loanId: {}");
        return ResponseEntity.ok(loanApplication);
    }

    @PutMapping("/approve")
    public ResponseEntity<LoanApplication> approveLoan(@Valid @RequestBody LoanUpdateRequest request) {
        logger.info("Approving loan with data: {}", request);
        LoanApplication loanApplication = loanService.approveLoan(request);
        logger.info("Loan approved with ID: {}");
        return ResponseEntity.ok(loanApplication);
    }
//
//
//    // Update an existing loan application
//    @PutMapping("/{loanId}")
//    public ResponseEntity<LoanApplication> updateLoanApplication(
//            @PathVariable Long loanId,
//            @Valid @RequestBody LoanUpdateRequest request) {
//        logger.info("Updating loan application for loanId: {} with data: {}", loanId, request);
//        LoanApplication loanApplication = loanService.updateLoanApplication(loanId, request);
//        logger.info("Updated loan application for loanId: {}", loanId);
//        return ResponseEntity.ok(loanApplication);
//    }
//
    // Cancel a loan application
    @DeleteMapping("/{loanId}")
    public ResponseEntity<Void> cancelLoanApplication(@PathVariable Long loanId) {
        logger.info("Cancelling loan application for loanId: {}", loanId);
        loanService.cancelLoanApplication(loanId);
        logger.info("Loan application cancelled for loanId: {}", loanId);
        return ResponseEntity.noContent().build();
    }

//    // Pay the processing fee for a loan application
//    @PostMapping("/{loanId}/processing-fee")
//    public ResponseEntity<PaymentResponse> payProcessingFee(
//            @PathVariable Long loanId,
//            @Valid @RequestBody ProcessingFeePaymentRequest request) {
//        logger.info("Processing fee payment for loanId: {} with request: {}", loanId, request);
//        PaymentResponse response = loanService.processProcessingFeePayment(loanId, request);
//        logger.info("Processing fee paid successfully for loanId: {}", loanId);
//        return ResponseEntity.ok(response);
//    }

    // Get all active loans for a user
    @GetMapping("/user/{customerId}/active")
    public ResponseEntity<List<LoanApplication>> getActiveLoansForUser(@PathVariable Long customerId) {
        logger.info("Fetching active loans for customerId: {}", customerId);
        List<LoanApplication> activeLoans = loanService.getActiveLoansForUser(customerId);
        logger.info("Fetched {} active loans for customerId: {}", activeLoans.size(), customerId);
        return ResponseEntity.ok(activeLoans);
    }

    // Get loan status by reference number
    @GetMapping("/status/{referenceNumber}")
    public ResponseEntity<LoanApplicationResponse> getLoanStatus(@PathVariable String referenceNumber) {
        logger.info("Fetching loan status for reference number: {}", referenceNumber);
        LoanApplicationResponse response = loanService.getLoanStatus(referenceNumber);
        logger.info("Fetched loan status for reference number: {}", referenceNumber);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/loan-types")
    public List<String> getLoanTypes() {
    	 logger.info("Fetching getLoanTypes : {}" ,loanService.getLoanTypes());
    	 return loanService.getLoanTypes();
    }
    
    @GetMapping("/interest-rate/{loanType}")
    public ResponseEntity<Double> getInterestRate(@PathVariable String loanType) {
        logger.info("Fetching interest rate for loan type: {}", loanType);
        Double interestRate = loanService.getInterestRateByLoanType(loanType);

        if (interestRate != null) {
            logger.info("Interest rate for {} is {}%", loanType, interestRate);
            return ResponseEntity.ok(interestRate);
        } else {
            logger.warn("Interest rate not found for loan type: {}", loanType);
            return ResponseEntity.notFound().build();
        }
    }

}
