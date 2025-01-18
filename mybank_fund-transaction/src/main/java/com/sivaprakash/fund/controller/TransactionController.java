package com.sivaprakash.fund.controller;

import java.time.LocalDateTime;
import java.util.List;

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
import org.springframework.web.client.RestTemplate;

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
    
	@Autowired
    private TransactionService transactionService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/")
	public String greatings(){
		return "Greatings from transactions ";
	}
	
    
	@GetMapping("/{accountNumber}/recent")
    public ResponseEntity<TransactionResponseDTO> getRecentTransactions(
            @PathVariable String accountNumber) {
        System.out.println("getRecentTransactions-----------------------------------");
        List<TransactionDTO> transactions = transactionService.getLastTenTransactions(accountNumber);
        
        TransactionResponseDTO response = new TransactionResponseDTO();
        response.setTransactions(transactions);
        response.setTotalElements(transactions.size());
        response.setMessage("Recent transactions retrieved successfully");
        System.out.println("getRecentTransactions-----------------------------------"+transactions);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/account/{accountId}/search")
    public ResponseEntity<TransactionResponseDTO> searchTransactions(
            @PathVariable String accountId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        
        TransactionSearchCriteria criteria = new TransactionSearchCriteria();
        criteria.setAccountId(accountId);
        criteria.setStartDate(startDate);
        criteria.setEndDate(endDate);

        List<TransactionDTO> transactions = transactionService.searchTransactions(criteria);
        
        TransactionResponseDTO response = new TransactionResponseDTO();
        response.setTransactions(transactions);
        response.setTotalElements(transactions.size());
        response.setMessage("Transactions retrieved successfully");
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/transfer")
    public ResponseEntity<TransferResponseDTO> transfer(@RequestBody TransferRequestDTO transferRequest) {
        // Create a Transaction object
    		
    	//    	
        Transaction fromTransaction = new Transaction();   	
        fromTransaction.setFromAccountNumber(transferRequest.getSelectedAccount());
        fromTransaction.setToAccountNumber(transferRequest.getBeneficiaryAccountNumber());
        fromTransaction.setAmount(transferRequest.getTransferAmount());
        fromTransaction.setTransactionType(Transaction.TransactionType.TRANSFER);
        fromTransaction.setTransactionDate(LocalDateTime.now());
        fromTransaction.setDescription("Fund Transfer : "+transferRequest.getSelectedAccount() +" to "+transferRequest.getBeneficiaryAccountNumber());

        Transaction toTransaction = new Transaction();   	
        toTransaction.setFromAccountNumber(transferRequest.getBeneficiaryAccountNumber());
        toTransaction.setToAccountNumber(transferRequest.getSelectedAccount());
        toTransaction.setAmount(transferRequest.getTransferAmount());
        toTransaction.setTransactionType(Transaction.TransactionType.DEPOSIT);
        toTransaction.setTransactionDate(LocalDateTime.now());
        toTransaction.setDescription("Deposited from "+transferRequest.getSelectedAccount());
        
        String url = "http://localhost:8082/api/v1/accounts/update-balance";
        
        // Call the transaction service to perform the transfer
        boolean success = transactionService.processTransfer(fromTransaction,toTransaction);

        
        
        restTemplate.patchForObject(url, transferRequest, UpdateBalanceResponseDTO.class);
//
//        restTemplate.patchForObject(url, transferRequest, UpdateBalanceResponseDTO.class);

        // Create a response DTO
        TransferResponseDTO response = new TransferResponseDTO();
        response.setSuccess(success);
        response.setMessage(success ? "Transfer completed successfully." : "Transfer failed.");

        return ResponseEntity.ok(response);    }
}