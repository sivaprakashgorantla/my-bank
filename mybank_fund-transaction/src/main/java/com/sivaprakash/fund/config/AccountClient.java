package com.sivaprakash.fund.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sivaprakash.fund.dto.TransferRequestDTO;
import com.sivaprakash.fund.dto.UpdateBalanceResponseDTO;

@FeignClient(name = "account-service", url = "http://localhost:8082/api/v1/accounts")
public interface AccountClient {

	@PutMapping("/update-balance")
    ResponseEntity<UpdateBalanceResponseDTO> updateBalance(@RequestBody TransferRequestDTO transferRequest);
}
