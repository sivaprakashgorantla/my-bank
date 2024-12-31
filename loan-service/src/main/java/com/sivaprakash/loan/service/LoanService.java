package com.sivaprakash.loan.service;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sivaprakash.loan.LoanApplicationRequest;
import com.sivaprakash.loan.LoanUpdateRequest;
import com.sivaprakash.loan.ProcessingFeePaymentRequest;
import com.sivaprakash.loan.entity.LoanApplication;
import com.sivaprakash.loan.enums.LoanStatus;
import com.sivaprakash.loan.kafka.EventPublisher;
import com.sivaprakash.loan.repository.LoanRepository;
import com.sivaprakash.loan.response.LoanApplicationResponse;
import com.sivaprakash.loan.response.PaymentResponse;

import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;

@Service
@Transactional
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private EventPublisher eventPublisher;

    public LoanApplicationResponse createLoanApplication(LoanApplicationRequest request) {
       
        validateLoanRequest(request);

        LoanApplication loan = buildLoanApplication(request);
        loan = loanRepository.save(loan);

        String referenceNumber = generateReferenceNumber(loan.getLoanId());
        loan.setReferenceNumber(referenceNumber);
        loanRepository.save(loan);

        eventPublisher.publishLoanCreatedEvent(loan);

        return buildLoanApplicationResponse(loan);
    }

    public LoanApplication getLoanApplication(Long loanId) {
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan not found"));
    }

    public LoanApplication updateLoanApplication(Long loanId, LoanUpdateRequest request) {
        LoanApplication loan = getLoanApplication(loanId);
        if (!loan.getStatus().equals(LoanStatus.PENDING)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only pending loans can be updated");
        }

        loan.setAmount(request.getAmount());
        loan.setPurpose(request.getPurpose());
        loan.setTermMonths(request.getTermMonths());
        return loanRepository.save(loan);
    }

    public void cancelLoanApplication(Long loanId) {
        LoanApplication loan = getLoanApplication(loanId);
        if (!loan.getStatus().equals(LoanStatus.PENDING)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only pending loans can be canceled");
        }

        loan.setStatus(LoanStatus.CANCELLED);
        loanRepository.save(loan);
    }

    public PaymentResponse processProcessingFeePayment(Long loanId, ProcessingFeePaymentRequest request) {
        LoanApplication loan = getLoanApplication(loanId);
        if (loan.getProcessingFeePaid()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Processing fee is already paid");
        }

        PaymentResponse paymentResponse = paymentService.payProcessingFee(loan, request);
        loan.setProcessingFeePaid(true);
        loan.setStatus(LoanStatus.PENDING);
        loanRepository.save(loan);

        eventPublisher.publishProcessingFeePaidEvent(loan);
        return paymentResponse;
    }

    public List<LoanApplication> getActiveLoansForUser(Long userId) {
        return null;//loanRepository.findByUserIdAndStatusNot(userId, LoanStatus.CANCELLED);
    }

    public LoanApplicationResponse getLoanStatus(String referenceNumber) {
        // Find the loan by reference number, throw an error if not found
        LoanApplication loan = loanRepository.findByReferenceNumber(referenceNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan not found"));

        // Return a response based on the found loan
        return buildLoanApplicationResponse(loan);
    }

    private LoanApplication buildLoanApplication(LoanApplicationRequest request) {
        LoanApplication loan = new LoanApplication();
        loan.setUserId(request.getUserId());
        loan.setAmount(request.getAmount());
        loan.setPurpose(request.getPurpose());
        loan.setTermMonths(request.getTermMonths());
        loan.setStatus(LoanStatus.PENDING);
        loan.setApplicationDate(LocalDateTime.now());
        loan.setProcessingFee(calculateProcessingFee(request.getAmount()));
        loan.setProcessingFeePaid(false);
        return loan;
    }

    private BigDecimal calculateProcessingFee(BigDecimal amount) {
        return amount.multiply(new BigDecimal("0.02")).setScale(2, RoundingMode.HALF_UP);
    }

    private String generateReferenceNumber(Long loanId) {
        return "REF-" + loanId + "-" + System.currentTimeMillis();
    }

    private LoanApplicationResponse buildLoanApplicationResponse(LoanApplication loan) {
        return LoanApplicationResponse.builder()
                .loanId(loan.getLoanId())
                .referenceNumber(loan.getReferenceNumber())
                .status(loan.getStatus())
                .amount(loan.getAmount())
                .processingFee(loan.getProcessingFee())
                .applicationDate(loan.getApplicationDate())
                .message("Loan details retrieved successfully")
                .build();
    }

    private void validateLoanRequest(LoanApplicationRequest request) {
        List<String> errors = new ArrayList<>(); // Collects all validation errors

        // Basic validation for user ID
        if (request.getUserId() == null) {
            errors.add("User ID is required");
        }

        // Loan amount must be greater than zero
        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            errors.add("Loan amount must be greater than zero");
        }

        // Loan purpose must not be empty
        if (request.getPurpose() == null || request.getPurpose().trim().isEmpty()) {
            errors.add("Loan purpose is required");
        }

        // Loan term must be between 3 and 60 months
        if (request.getTermMonths() == null || request.getTermMonths() < 3 || request.getTermMonths() > 60) {
            errors.add("Loan term must be between 3 and 60 months");
        }

        // Customer details validation
        if (request.getCustomerDetails() != null) {
            // Monthly income must be greater than zero (if provided)
            if (request.getCustomerDetails().getMonthlyIncome() != null && 
                request.getCustomerDetails().getMonthlyIncome().compareTo(BigDecimal.ZERO) <= 0) {
                errors.add("Monthly income must be greater than zero");
            }
        }

        // Throw an exception if any validation errors exist
        if (!errors.isEmpty()) {
            throw new ValidationException("Validation failed: " + String.join(", ", errors));
        }
    }
}
