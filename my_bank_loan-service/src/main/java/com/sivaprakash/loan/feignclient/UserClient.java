package com.sivaprakash.loan.feignclient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sivaprakash.loan.response.CustomerResponse;

// Define Feign Client to interact with the User Service
@FeignClient(name = "user-service", url = "http://localhost:8081/api/v1/customers")
public interface UserClient {

    @GetMapping("/customer/{customerId}")
    CustomerResponse getCustomerById(@PathVariable("customerId") Long customerId);
}
