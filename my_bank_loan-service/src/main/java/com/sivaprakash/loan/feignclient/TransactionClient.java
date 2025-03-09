package com.sivaprakash.loan.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.sivaprakash.loan.request.TransferRequestDTO;
import com.sivaprakash.loan.response.TransferResponseDTO;

@FeignClient(name = "transaction-service", url = "http://localhost:8083/api/v1/transactions")
public interface TransactionClient {

    @PostMapping("/transfer")
    ResponseEntity<TransferResponseDTO> transfer(@RequestBody TransferRequestDTO transactionDTO);
    
    @GetMapping("/")
    String greatings();
    

    @PostMapping("/wish")
    String wish(@RequestParam("name") String name);
}
