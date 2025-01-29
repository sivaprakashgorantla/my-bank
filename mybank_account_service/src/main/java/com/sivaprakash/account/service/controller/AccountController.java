package com.sivaprakash.account.service.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sivaprakash.account.service.dto.AccountDetailsDTO;
import com.sivaprakash.account.service.dto.AccountResponseDTO;
import com.sivaprakash.account.service.dto.TransferRequestDTO;
import com.sivaprakash.account.service.dto.UpdateBalanceResponseDTO;
import com.sivaprakash.account.service.service.AccountService;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private AccountService accountService;

	@GetMapping("/customer/{customerId}")
	public ResponseEntity<AccountResponseDTO> getAccountsByCustomerId(@PathVariable String customerId) {
		logger.info("Fetching accounts for customer ID: {}", customerId);

		List<AccountDetailsDTO> accounts = accountService.getAccountsByUserCustomerId(customerId);
		logger.debug("Account details retrieved: {}", accounts);

		if (accounts.isEmpty()) {
			logger.warn("No accounts found for customer ID: {}", customerId);
			return ResponseEntity.ok().body(new AccountResponseDTO("No accounts found for the user", null));
		}

		return ResponseEntity.ok().body(new AccountResponseDTO("Accounts retrieved successfully", accounts));
	}

	@GetMapping("/{accountNumber}")
	public ResponseEntity<AccountResponseDTO> getAccountsByUserCustomerId(@PathVariable String accountNumber) {
		logger.info("Fetching accounts for account number: {}", accountNumber);

		List<AccountDetailsDTO> accounts = accountService.getAccountsDetailsByAccountNumber(accountNumber);
		logger.debug("Account details retrieved: {}", accounts);

		if (accounts.isEmpty()) {
			logger.warn("No accounts found for account number: {}", accountNumber);
			return ResponseEntity.ok().body(new AccountResponseDTO("No accounts found for the user", null));
		}

		return ResponseEntity.ok().body(new AccountResponseDTO("Accounts retrieved successfully", accounts));
	}

	@PatchMapping("/update-balance")
	public ResponseEntity<UpdateBalanceResponseDTO> updateAccountBalance(
			@RequestBody TransferRequestDTO transferRequestDTO) {
		logger.info("Updating account balances for transfer: {}", transferRequestDTO);

		try {
			boolean debitSuccess = accountService.updateAccountBalance(transferRequestDTO.getSelectedAccount(),
					transferRequestDTO.getTransferAmount(), "DEBIT");

			boolean creditSuccess = accountService.updateAccountBalance(
					transferRequestDTO.getBeneficiaryAccountNumber(), transferRequestDTO.getTransferAmount(), "CREDIT");

			boolean success = debitSuccess && creditSuccess;

			logger.debug("Debit success: {}, Credit success: {}", debitSuccess, creditSuccess);

			UpdateBalanceResponseDTO response = new UpdateBalanceResponseDTO();
			response.setSuccess(success);
			response.setMessage(
					success ? "Account balance updated successfully." : "Failed to update account balance.");

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			logger.error("Error occurred while updating balance: {}", e.getMessage(), e);

			UpdateBalanceResponseDTO errorResponse = new UpdateBalanceResponseDTO();
			errorResponse.setSuccess(false);
			errorResponse.setMessage("Failed to update balance: " + e.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}

	@GetMapping("/all")
	public ResponseEntity<AccountResponseDTO> getAccounts() {
	    logger.info("Fetching all accounts.");

	    try {
	        List<AccountDetailsDTO> accounts = accountService.getAccounts();
	        logger.debug("Account details retrieved: {}", accounts);

	        if (accounts.isEmpty()) {
	            logger.warn("No accounts found.");
	            return ResponseEntity.ok().body(new AccountResponseDTO("No accounts found.", null));
	        }

	        return ResponseEntity.ok().body(new AccountResponseDTO("Accounts retrieved successfully", accounts));
	    } catch (Exception e) {
	        logger.error("Error occurred while fetching all accounts: {}", e.getMessage(), e);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new AccountResponseDTO("Error fetching accounts.", null));
	    }
	}
	
}