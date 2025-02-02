package com.sivaprakash.user_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "account-service", url = "http://localhost:8082/api/v1/accounts")
public interface AccountFeignClient {
    @PostMapping("/create/{customerId}")
    boolean createAccount(@PathVariable("customerId") Long customerId);
}
