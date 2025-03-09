package com.sivaprakash.fund.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sivaprakash.fund.config.AccountClient;
import com.sivaprakash.fund.config.BeneficiaryClient;
import com.sivaprakash.fund.dto.BeneficiaryResponseDTO;
import com.sivaprakash.fund.dto.TransactionDTO;
import com.sivaprakash.fund.dto.TransactionResponseDTO;
import com.sivaprakash.fund.dto.TransactionSearchCriteria;
import com.sivaprakash.fund.dto.TransferRequestDTO;
import com.sivaprakash.fund.dto.TransferResponseDTO;
import com.sivaprakash.fund.dto.UpdateBalanceResponseDTO;
import com.sivaprakash.fund.entity.Transaction;
import com.sivaprakash.fund.service.TransactionService;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

	private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

	@Autowired
	private TransactionService transactionService;


	@Autowired
	private AccountClient accountClient;

	@Autowired
	private BeneficiaryClient beneficiaryClient;

	@GetMapping("/")
	public String greatings() {
		return "Greatings from transactions ";
	}

	@PostMapping("/wish")
	public String wish(@RequestParam("name") String name) {
		return "Greatings from transactions "+name;
	}

	@GetMapping("/{accountNumber}/recent")
	public ResponseEntity<TransactionResponseDTO> getRecentTransactions(@PathVariable String accountNumber) {
		System.out.println("getRecentTransactions-----------------------------------");
		logger.info("Fetching recent transactions for account: {}", accountNumber);
        
		List<TransactionDTO> transactions = transactionService.getLastTenTransactions(accountNumber);

		TransactionResponseDTO response = new TransactionResponseDTO();
		response.setTransactions(transactions);
		response.setTotalElements(transactions.size());
		response.setMessage("Recent transactions retrieved successfully");
		System.out.println("getRecentTransactions-----------------------------------" + transactions);
		logger.info("Recent transactions found: {}", transactions.size());
	       
		return ResponseEntity.ok(response);
	}

	@GetMapping("/account/{accountId}/search")
	public ResponseEntity<TransactionResponseDTO> searchTransactions(@PathVariable String accountId,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
		logger.info("Searching transactions for account: {}, StartDate: {}, EndDate: {}", accountId, startDate, endDate);

		TransactionSearchCriteria criteria = new TransactionSearchCriteria();
		criteria.setAccountId(accountId);
		criteria.setStartDate(startDate);
		criteria.setEndDate(endDate);

		List<TransactionDTO> transactions = transactionService.searchTransactions(criteria);

		TransactionResponseDTO response = new TransactionResponseDTO();
		response.setTransactions(transactions);
		response.setTotalElements(transactions.size());
		response.setMessage("Transactions retrieved successfully");
		logger.info("Total transactions found: {}", transactions.size());
        return ResponseEntity.ok(response);	
    }

//	@PostMapping("/transfer")
//	public ResponseEntity<TransferResponseDTO> transfer(@RequestBody TransferRequestDTO transferRequest) {
//
//		BeneficiaryResponseDTO beneficiary = beneficiaryClient
//				.getBeneficiaryByCustomerId(transferRequest.getBeneficiaryId());
//		if (beneficiary == null) {
//			return ResponseEntity.badRequest().body(new TransferResponseDTO(false, "Beneficiary not found!"));
//		}
//		//passing beneficiary account no.  so setting beneficiary account number 
//		beneficiary.setBeneficiaryAccountNumber(beneficiary.getBeneficiaryAccountNumber());
//		transferRequest.setBeneficiaryAccountNumber(beneficiary.getBeneficiaryAccountNumber());
//
//
//		Transaction toTransaction = getToTransaction(transferRequest, beneficiary); // String url =
//																					// "http://account-service/api/v1/accounts/update-balance";
//		Transaction fromTransaction = null;
//		// Process the transaction
//		boolean success = false;
//		if(fromTransaction==null) {
//			transactionService.processTransfer(fromTransaction, toTransaction,
//					null, null);
//			
//		}else {
//			fromTransaction = getFromTransaction(transferRequest, beneficiary);
//			success = transactionService.processTransfer(fromTransaction, toTransaction,
//					beneficiary.getBeneficiaryBankCode(), beneficiary.getBeneficiaryName());
//		}
//		if (!success) {
//			return ResponseEntity.badRequest().body(new TransferResponseDTO(false, "Transaction failed"));
//		}
//
//		// Update balance using Feign Client
//		ResponseEntity<UpdateBalanceResponseDTO> response = accountClient.updateBalance(transferRequest);
//
//		
//		System.out.println("update balance response : " + response);
//		TransferResponseDTO transferResponse = new TransferResponseDTO();
//		transferResponse.setSuccess(true);
//		transferResponse.setMessage("Transfer completed successfully.");
//		return ResponseEntity.ok(transferResponse);
//	}
	@PostMapping("/transfer")
    public ResponseEntity<TransferResponseDTO> transfer(@RequestBody TransferRequestDTO transferRequest) {
        logger.info("Initiating transfer: {}", transferRequest);
        
        BeneficiaryResponseDTO beneficiary;
        
        if ("COMPANY-TRANSFER".equals(transferRequest.getTranserType())) {
            logger.info("Company transfer detected, setting up beneficiary manually.");
            beneficiary = new BeneficiaryResponseDTO();
            beneficiary.setBeneficiaryAccountNumber(transferRequest.getBeneficiaryAccountNumber());
        } else {
            logger.info("Fetching beneficiary details from BeneficiaryClient.");
            beneficiary = beneficiaryClient.getBeneficiaryByCustomerId(transferRequest.getBeneficiaryId());
        }

        if (beneficiary == null) {
            logger.warn("Beneficiary not found for ID: {}", transferRequest.getBeneficiaryId());
            return ResponseEntity.badRequest().body(new TransferResponseDTO(false, "Beneficiary not found!"));
        }

        transferRequest.setBeneficiaryAccountNumber(beneficiary.getBeneficiaryAccountNumber());

        Transaction fromTransaction = getFromTransaction(transferRequest, beneficiary);
        Transaction toTransaction = getToTransaction(transferRequest, beneficiary);

        logger.info("Processing transfer transaction...");
        boolean success = transactionService.processTransfer(fromTransaction, toTransaction);

        if (!success) {
            logger.error("Transaction failed for transfer request: {}", transferRequest);
            return ResponseEntity.badRequest().body(new TransferResponseDTO(false, "Transaction failed"));
        }

        logger.info("Updating balance via AccountClient...");
        ResponseEntity<UpdateBalanceResponseDTO> response = accountClient.updateBalance(transferRequest);

        logger.info("Update balance response: {}", response);
        TransferResponseDTO transferResponse = new TransferResponseDTO();
        transferResponse.setSuccess(true);
        transferResponse.setMessage("Transfer completed successfully.");

        return ResponseEntity.ok(transferResponse);
    }
	
	private Transaction getFromTransaction(TransferRequestDTO transferRequest, BeneficiaryResponseDTO beneficiary) {
		// TODO Auto-generated method stub
		logger.info("Creating FROM transaction for account: {}", transferRequest.getSelectedAccount());

		Transaction fromTransaction = new Transaction();
		fromTransaction.setFromAccountNumber(transferRequest.getSelectedAccount());
		//fromTransaction.setToAccountNumber(beneficiary.getBeneficiaryAccountNumber());
		fromTransaction.setAmount(transferRequest.getTransferAmount());
		fromTransaction.setTransactionType(Transaction.TransactionType.DEBIT);
		fromTransaction.setTransactionDate(LocalDateTime.now());
		fromTransaction.setReferenceNumber(generateTransactionId(beneficiary));
		fromTransaction.setDescription("Fund Debited : " + beneficiary.getBeneficiaryAccountNumber() + " to ");
		return fromTransaction;
	}

	private Transaction getToTransaction(TransferRequestDTO transferRequest, BeneficiaryResponseDTO beneficiary) {
		// TODO Auto-generated method stub
		 logger.info("Creating TO transaction for beneficiary account: {}", beneficiary.getBeneficiaryAccountNumber());

		Transaction toTransaction = new Transaction();
		toTransaction.setFromAccountNumber(beneficiary.getBeneficiaryAccountNumber());
		//toTransaction.setToAccountNumber(transferRequest.getSelectedAccount());
		toTransaction.setAmount(transferRequest.getTransferAmount());
		toTransaction.setTransactionType(Transaction.TransactionType.CREDIT);
		toTransaction.setTransactionDate(LocalDateTime.now());
		toTransaction.setReferenceNumber(generateTransactionId(beneficiary));
		toTransaction.setDescription("Deposited from " + transferRequest.getSelectedAccount());

		return toTransaction;
	}

	// Generate a unique transaction ID based on BankCode, Branch, and current
	// date/time
	private String generateTransactionId(BeneficiaryResponseDTO beneficiary) {
		logger.info("Generating transaction ID for beneficiary: {}", beneficiary.getBeneficiaryName());

		String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmssSSS"));
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "TXN "+beneficiary.getBeneficiaryName()+beneficiary.getBeneficiaryBankCode() + currentDateTime;
	
	}
}