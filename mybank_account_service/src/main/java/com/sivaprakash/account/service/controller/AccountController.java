package com.sivaprakash.account.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sivaprakash.account.service.dto.AccountDetailsDTO;
import com.sivaprakash.account.service.dto.AccountResponseDTO;
import com.sivaprakash.account.service.service.AccountService;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
    
	@Autowired
    private AccountService accountService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<AccountResponseDTO> getAccountsByUserId(
            @PathVariable Long userId) {
        System.out.println("AccountController ..............."+userId);
        // First verify if the user profile is complete
        if (accountService.isProfileComplete(userId)) {
            return ResponseEntity.badRequest()
                .body(new AccountResponseDTO("Please complete your profile first", null));
        }

        // Get all accounts for the user
        List<AccountDetailsDTO> accounts = accountService.getAccountsByUserId(userId);
        System.out.println("user details : "+accounts);
        if (accounts.isEmpty()) {
            return ResponseEntity.ok()
                .body(new AccountResponseDTO("No accounts found for the user", null));
        }

        return ResponseEntity.ok()
            .body(new AccountResponseDTO("Accounts retrieved successfully", accounts));
    }
    
}