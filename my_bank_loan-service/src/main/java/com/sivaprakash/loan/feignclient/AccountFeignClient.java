package com.sivaprakash.loan.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sivaprakash.loan.request.TransferRequestDTO;
import com.sivaprakash.loan.response.AccountResponseDTO;
import com.sivaprakash.loan.response.UpdateBalanceResponseDTO;

@FeignClient(name = "account-service", url = "http://localhost:8082/api/v1/accounts")
public interface AccountFeignClient {

    @PutMapping("/update-balance")
    ResponseEntity<UpdateBalanceResponseDTO> updateAccountBalance(@RequestBody TransferRequestDTO transferRequestDTO);
    
    @GetMapping("/customer/{customerId}")
    ResponseEntity<AccountResponseDTO> getAccountsByCustomerId(@PathVariable("customerId") Long customerId);

    @GetMapping("/company-account") // Add this method
    ResponseEntity<AccountResponseDTO> getCompanyAccount();
}
