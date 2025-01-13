package com.sivaprakash.fund.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sivaprakash.fund.dto.TransactionDTO;
import com.sivaprakash.fund.dto.TransactionResponseDTO;
import com.sivaprakash.fund.dto.TransactionSearchCriteria;
import com.sivaprakash.fund.service.TransactionService;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    
	@Autowired
    private TransactionService transactionService;
	
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
}