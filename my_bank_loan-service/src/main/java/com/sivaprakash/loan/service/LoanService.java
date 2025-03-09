package com.sivaprakash.loan.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sivaprakash.loan.entity.LoanApplication;
import com.sivaprakash.loan.entity.LoanType;
import com.sivaprakash.loan.enums.LoanStatus;
import com.sivaprakash.loan.feignclient.AccountFeignClient;
import com.sivaprakash.loan.feignclient.TransactionClient;
import com.sivaprakash.loan.feignclient.UserClient;
import com.sivaprakash.loan.repository.LoanRepository;
import com.sivaprakash.loan.request.LoanApplicationRequest;
import com.sivaprakash.loan.request.LoanUpdateRequest;
import com.sivaprakash.loan.request.TransferRequestDTO;
import com.sivaprakash.loan.response.AccountDetailsDTO;
import com.sivaprakash.loan.response.AccountResponseDTO;
import com.sivaprakash.loan.response.CustomerResponse;
import com.sivaprakash.loan.response.LoanApplicationResponse;
import com.sivaprakash.loan.response.TransferResponseDTO;
import com.sivaprakash.loan.response.UpdateBalanceResponseDTO;

import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;

@Service
@Transactional
public class LoanService {

	private static final Logger logger = LoggerFactory.getLogger(LoanService.class);

	@Autowired
	private LoanRepository loanRepository;

	@Autowired
	private UserClient userClient;

	@Autowired
	private AccountFeignClient accountFeignClient;

	@Autowired
	private TransactionClient transactionClient;
	

	public LoanApplicationResponse createLoanApplication(LoanApplicationRequest request) {
		logger.info("Creating loan application for customer ID: {}", request.getCustomerId());

		validateLoanRequest(request);

		LoanApplication loan = buildLoanApplication(request);
		//loan = loanRepository.save(loan);
		BigDecimal bigDecimalValue = BigDecimal.valueOf(loan.getLoanType().getInterestRate());
		loan.setInterestRate(bigDecimalValue);
		String referenceNumber = generateReferenceNumber(loan.getLoanId());
		loan.setReferenceNumber(referenceNumber);
		loanRepository.save(loan);

		logger.info("Loan application created successfully with Reference Number: {}", referenceNumber);

		return buildLoanApplicationResponse(loan);
	}

	public LoanApplication getLoanApplication(Long loanId) {
		logger.info("Fetching loan application with ID: {}", loanId);
		return loanRepository.findById(loanId).orElseThrow(() -> {
			logger.error("Loan not found with ID: {}", loanId);
			return new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan not found");
		});
	}

	public LoanApplication updateLoanApplication(Long loanId, LoanUpdateRequest request) {
		logger.info("Updating loan application with ID: {}", loanId);
		LoanApplication loan = getLoanApplication(loanId);

		if (!loan.getStatus().equals(LoanStatus.PENDING)) {
			logger.warn("Attempted update on non-pending loan ID: {}", loanId);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only pending loans can be updated");
		}

		loan.setLoanAmount(request.getAmount());
		loan.setLoanPurpose(request.getLoanPurpose());
		loan.setTermMonths(request.getTermMonths());
		logger.info("Loan application updated successfully for loan ID: {}", loanId);
		return loanRepository.save(loan);
	}

	public void cancelLoanApplication(Long loanId) {
		logger.info("Cancelling loan application with ID: {}", loanId);
		LoanApplication loan = getLoanApplication(loanId);

		if (!loan.getStatus().equals(LoanStatus.PENDING)) {
			logger.warn("Attempted cancellation of non-pending loan ID: {}", loanId);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only pending loans can be canceled");
		}

		loan.setStatus(LoanStatus.CANCELLED);
		loanRepository.save(loan);
		logger.info("Loan application cancelled successfully for loan ID: {}", loanId);
	}

//    public PaymentResponse processProcessingFeePayment(Long loanId, ProcessingFeePaymentRequest request) {
//        logger.info("Processing fee payment for loan ID: {}", loanId);
//        LoanApplication loan = getLoanApplication(loanId);
//
//        if (loan.getProcessingFeePaid()) {
//            logger.warn("Processing fee already paid for loan ID: {}", loanId);
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Processing fee is already paid");
//        }
//
//        PaymentResponse paymentResponse = paymentService.payProcessingFee(loan, request);
//        loan.setProcessingFeePaid(true);
//        loan.setStatus(LoanStatus.PENDING);
//        loanRepository.save(loan);
//
//        eventPublisher.publishProcessingFeePaidEvent(loan);
//        logger.info("Processing fee payment completed successfully for loan ID: {}", loanId);
//        return paymentResponse;
//    }

	public List<LoanApplication> getActiveLoansForUser(Long customerId) {
		logger.info("Fetching active loans for customer ID: {}", customerId);
		return loanRepository.findByCustomerIdAndStatusNot(customerId, LoanStatus.CANCELLED);
	}

//    public LoanApplicationResponse getLoanStatus(String referenceNumber) {
//        logger.info("Fetching loan status for reference number: {}", referenceNumber);
//        LoanApplication loan = loanRepository.findByReferenceNumber(referenceNumber)
//                .orElseThrow(() -> {
//                    logger.error("Loan not found with reference number: {}", referenceNumber);
//                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan not found");
//                });
//
//        logger.info("Loan status retrieved successfully for reference number: {}", referenceNumber);
//        return buildLoanApplicationResponse(loan);
//    }

	public LoanApplicationResponse getLoanStatus(String referenceNumber) {
		logger.info("Fetching loan status for reference number: {}", referenceNumber);

		LoanApplication loan = loanRepository.findByReferenceNumber(referenceNumber).orElseThrow(() -> {
			logger.error("Loan not found with reference number: {}", referenceNumber);
			return new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan not found");
		});

		// Fetch Customer Details using Feign Client
		CustomerResponse customer = userClient.getCustomerById(loan.getCustomerId());

		logger.info("CustomerResponse: {}", customer);

		logger.info("Loan status retrieved successfully for reference number: {}", referenceNumber);

		return new LoanApplicationResponse.Builder().loanId(loan.getLoanId()).customerId(loan.getCustomerId())
				.customerName(customer.getFirstName() + " " + customer.getLastName()) // Set fetched customer name
				.referenceNumber(loan.getReferenceNumber()).status(loan.getStatus()).loanAmount(loan.getLoanAmount())
				.loanType(loan.getLoanType()).termMonths(loan.getTermMonths()).processingFee(loan.getProcessingFee())
				.applicationDate(loan.getApplicationDate()).build();
	}

	private LoanApplication buildLoanApplication(LoanApplicationRequest request) {
		logger.debug("Building loan application for customer ID: {}", request.getCustomerId());
		LoanApplication loan = new LoanApplication();
		loan.setCustomerId(request.getCustomerId());
		loan.setLoanAmount(request.getLoanAmount());
		loan.setLoanPurpose(request.getLoanPurpose());
		loan.setTermMonths(request.getTermMonths());
		loan.setStatus(LoanStatus.PENDING);
		loan.setApplicationDate(LocalDateTime.now());
		loan.setProcessingFee(calculateProcessingFee(request.getLoanAmount()));
		loan.setProcessingFeePaid(false);
		LoanType loanType = LoanType.valueOf(request.getLoanType());
		loan.setLoanType(loanType);
		return loan;
	}

	private BigDecimal calculateProcessingFee(BigDecimal amount) {
		logger.debug("Calculating processing fee for amount: {}", amount);
		return amount.multiply(new BigDecimal("0.02")).setScale(2, RoundingMode.HALF_UP);
	}

	private String generateReferenceNumber(Long loanId) {
	    return String.format("REF-%d-%s", loanId, LocalDateTime.now().toString().replace(":", "").replace("-", ""));
	}

	private LoanApplicationResponse buildLoanApplicationResponse(LoanApplication loan) {
		logger.debug("Building loan response for loan ID: {}", loan.getLoanId());
		return new LoanApplicationResponse.Builder().loanId(loan.getLoanId()).referenceNumber(loan.getReferenceNumber())
				.status(loan.getStatus()).loanAmount(loan.getLoanAmount()).processingFee(loan.getProcessingFee())
				.applicationDate(loan.getApplicationDate()).build();
	}

	private void validateLoanRequest(LoanApplicationRequest request) {
		List<String> errors = new ArrayList<>();
		logger.debug("Validating loan request for customer ID: {}", request.getCustomerId());

		if (Objects.isNull(request.getCustomerId())) {
		    throw new ValidationException("Customer ID is required");
		}
		if (request.getLoanAmount() == null || request.getLoanAmount().compareTo(BigDecimal.ZERO) <= 0) {
		    throw new ValidationException("Loan amount must be greater than zero");
		}
		if (request.getLoanPurpose() == null || request.getLoanPurpose().trim().isEmpty()) {
			errors.add("Loan purpose is required");
		}
		if (request.getTermMonths() == null || request.getTermMonths() < 3 || request.getTermMonths() > 60) {
			errors.add("Loan term must be between 3 and 60 months");
		}
		if (!errors.isEmpty()) {
			logger.error("Validation failed: {}", String.join(", ", errors));
			throw new ValidationException("Validation failed: " + String.join(", ", errors));
		}
		logger.debug("Loan request validated successfully");
	}

	public List<LoanApplication> allApprovals() {
		logger.info("Fetching all loan approvals");
		return loanRepository.findAll();
	}

	public List<String> getLoanTypes() {
		// TODO Auto-generated method stub
		logger.info("getLoanTypes all loan approvals");
		return Arrays.stream(LoanType.values()).map(Enum::name).toList();
	}

	public Double getInterestRateByLoanType(String loanType) {
		// TODO Auto-generated method stub
		LoanType type = LoanType.valueOf(loanType.toUpperCase());
		return type.getInterestRate();
	}

/*	public LoanApplication approveLoan(LoanUpdateRequest request) {
	    logger.info("approveLoan Approving loan with ID: {}", request.getLoanId());
	    logger.info("approveLoan Approving loan with remark: {}", request.getRemarkInput());
	    LoanApplication loan = getLoanApplication(request.getLoanId());

	    if (!loan.getStatus().equals(LoanStatus.PENDING)) {
	        logger.warn("Attempted approval on non-pending loan ID: {}", request.getLoanId());
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only pending loans can be approved");
	    }

	    loan.setStatus(LoanStatus.APPROVED);
	    loan.setRemarkInput(request.getRemarkInput()); // Save the remark
	    loan.setApprovalDate(LocalDateTime.now());
	    loanRepository.save(loan);
	    logger.info("Loan approved successfully for loan ID: {}", request.getLoanId());

	    // Call Feign Client to update account balance
        try {
        	
        	ResponseEntity<AccountResponseDTO> response = accountFeignClient.getAccountsByCustomerId(loan.getCustomerId());

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                logger.info("Account details fetched successfully for customer ID: {}", loan.getCustomerId());

                BigDecimal amount = response.getBody().getAccounts().get(0).getBalance();
                String accountNumber = response.getBody().getAccounts().get(0).getAccountNumber();
                BigDecimal balenceLoanAmount = loan.getLoanAmount().subtract(loan.getProcessingFee());
                System.out.println("Inicial balance :"+amount);
                System.out.println("Loan amount  balance :"+loan.getLoanAmount());
                System.out.println("Loan Processing amount amount  balance :"+loan.getProcessingFee());
                
                BigDecimal totalAmount = amount.add(balenceLoanAmount) ;
//                TransferRequestDTO transferRequestDTO = new TransferRequestDTO();
//                transferRequestDTO.setCustomerId(loan.getCustomerId());
//                transferRequestDTO.setSelectedAccount(accountNumber);
//                transferRequestDTO.setTransferAmount(amount.add(totalAmount));
//                ResponseEntity<UpdateBalanceResponseDTO> accountDetails = accountFeignClient.updateAccountBalance(transferRequestDTO);
//                logger.info("No account details found for customer ID: {}", accountDetails);
//            
                TransferRequestDTO transactionDTO = new TransferRequestDTO();
	            //transactionDTO.setFromAccountNumber("LOAN_DISBURSEMENT");
	            transactionDTO.setSelectedAccount(accountNumber);
	            transactionDTO.setTransferAmount(balenceLoanAmount);

	            // Call Transaction Microservice using Feign Client
	            ResponseEntity<TransactionResponseDTO> transactionResponse = transactionClient.createTransaction(transactionDTO);

	            if (transactionResponse.getStatusCode().is2xxSuccessful()) {
	                logger.info("Transaction recorded successfully: {}", transactionResponse.getBody());
	            } else {
	                logger.error("Failed to record transaction for loan disbursement.");
	                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to record transaction.");
	            }
	        } else {
	            logger.warn("No account details found for customer ID: {}", loan.getCustomerId());
	        }

             
            
                logger.info("Account balance updated successfully."+response.getBody());
        }catch(

	Exception e)
	{
		logger.error("Error updating account balance: {}", e.getMessage(), e);
		throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update account balance.");
	}
*/public LoanApplication approveLoan(LoanUpdateRequest request) {
	    logger.info("Approving loan with ID: {}", request.getLoanId());
	    LoanApplication loan = getLoanApplication(request.getLoanId());

	    if (!loan.getStatus().equals(LoanStatus.PENDING)) {
	        logger.warn("Attempted approval on non-pending loan ID: {}", request.getLoanId());
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only pending loans can be approved");
	    }
	    
	    try {
		
	    	if ("APPROVED".equals(request.getStatus().toUpperCase())) { 
	    	    loan.setStatus(LoanStatus.APPROVED);
	    	}else {
	    	    loan.setStatus(LoanStatus.REJECTED);  // Or any other appropriate status
	    	}
			loan.setRemarkInput(request.getRemarkInput());
			loan.setApprovalDate(LocalDateTime.now());
			logger.info("Updating loan status for ID: {} with status: {}", request.getLoanId(), loan.getStatus());
			loanRepository.save(loan);
			logger.info("Loan status updated successfully.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//logger.info("Error whle same : ",e.printStackTrace());
		}

	    try {
	    	if ("REJECTED".equals(request.getStatus().toUpperCase())) { 
	    	    loan.setStatus(LoanStatus.REJECTED);
	    	}

	    	AccountDetailsDTO companyAccountDetailsDTO = getCompanyAccount();
	        // Fetch company (lender's) account details
	        

	        String companyAccountNumber = companyAccountDetailsDTO.getAccountNumber();
	        BigDecimal companyBalance = companyAccountDetailsDTO.getBalance();

	        BigDecimal disbursementAmount = Optional.ofNullable(loan.getLoanAmount())
	        	    .orElse(BigDecimal.ZERO)
	        	    .subtract(Optional.ofNullable(loan.getProcessingFee()).orElse(BigDecimal.ZERO));


	        checkCompanyAccountBalance(companyBalance,disbursementAmount,companyAccountNumber);
	        
	        ResponseEntity<AccountResponseDTO> customerAccountResponse = accountFeignClient.getAccountsByCustomerId(loan.getCustomerId());

	        Optional.ofNullable(customerAccountResponse.getBody())
	                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch customer account details"));


	        String customerAccountNumber = customerAccountResponse.getBody().getAccounts().get(0).getAccountNumber();

	        TransferRequestDTO transactionDTO = new TransferRequestDTO();
	        transactionDTO.setSelectedAccount(companyAccountNumber );
	        transactionDTO.setCustomerId(loan.getCustomerId());
	        transactionDTO.setBeneficiaryAccountNumber(customerAccountNumber);
	        transactionDTO.setTransferAmount(disbursementAmount);
	        transactionDTO.setTranserType("COMPANY-TRANSFER");
	        String str = transactionClient.greatings();
	        String str1 = transactionClient.wish("Sivaprakash Gorntla");
	        ResponseEntity<TransferResponseDTO> transactionResponse = transactionClient.transfer(transactionDTO);
	        if (!transactionResponse.getStatusCode().is2xxSuccessful()) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to record loan disbursement transaction");
	        }

	        logger.info("Loan amount disbursed successfully to customer account: {}", customerAccountNumber);
	    } catch (Exception e) {
	        logger.error("Error in loan approval process: {}", e.getMessage(), e);
	        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Loan approval process failed");
	    }

	    return loan;
	}

private void checkCompanyAccountBalance(BigDecimal companyBalance, BigDecimal disbursementAmount,String companyAccountNumber) {
	// TODO Auto-generated method stub
	// Check if company balance is sufficient
    if (companyBalance.compareTo(disbursementAmount) < 0) {
        BigDecimal reloadAmount = disbursementAmount.subtract(companyBalance).add(new BigDecimal("10000000")); // Extra buffer
        logger.info("Insufficient funds in company account. Reloading with amount: {}", reloadAmount);

        TransferRequestDTO reloadRequest = new TransferRequestDTO();
        reloadRequest.setSelectedAccount(companyAccountNumber);
        reloadRequest.setTransferAmount(reloadAmount);

        ResponseEntity<UpdateBalanceResponseDTO> reloadResponse = accountFeignClient.updateAccountBalance(reloadRequest);

        if (!reloadResponse.getStatusCode().is2xxSuccessful()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to reload company account");
        }

        logger.info("Company account reloaded successfully with amount: {}", reloadAmount);
    }

}

private AccountDetailsDTO getCompanyAccount() {
	// TODO Auto-generated method stub
	ResponseEntity<AccountResponseDTO> companyAccountResponse = accountFeignClient.getCompanyAccount();
    if (!companyAccountResponse.getStatusCode().is2xxSuccessful() || companyAccountResponse.getBody() == null) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch company account details");
    }
	return  companyAccountResponse.getBody().getAccounts().get(0);
}

}
