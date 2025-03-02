package com.sivaprakash.loan.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sivaprakash.loan.request.TransferRequestDTO;
import com.sivaprakash.loan.response.TransactionResponseDTO;

@FeignClient(name = "transaction-service", url = "http://localhost:8083/api/v1/transactions")
public interface TransactionClient {

    @PostMapping("/transfer")
    ResponseEntity<TransactionResponseDTO> createTransaction(@RequestBody TransferRequestDTO transactionDTO);
}
