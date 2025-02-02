package com.sivaprakash.account.service.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sivaprakash.account.service.entiry.AccountType;
import com.sivaprakash.account.service.service.AccountTypeService;

@RestController
@RequestMapping("/api/account-types")
public class AccountTypeController {

    @Autowired
    private AccountTypeService accountTypeService;

    // Get all account types
    @GetMapping
    public List<AccountType> getAllAccountTypes() {
        return accountTypeService.findAll();
    }

    // Get account type by ID
    @GetMapping("/{id}")
    public ResponseEntity<AccountType> getAccountTypeById(@PathVariable Long id) {
        Optional<AccountType> accountType = accountTypeService.findById(id);
        return accountType.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new account type
    @PostMapping
    public ResponseEntity<AccountType> createAccountType(@RequestBody AccountType accountType) {
        AccountType createdAccountType = accountTypeService.save(accountType);
        return new ResponseEntity<>(createdAccountType, HttpStatus.CREATED);
    }

    // Delete an account type by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccountType(@PathVariable Long id) {
        accountTypeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Example of custom query: Find account types by name
    @GetMapping("/type-name/{typeName}")
    public AccountType getAccountTypesByTypeName(@PathVariable String typeName) {
        return accountTypeService.findByTypeName(typeName);
    }
}
