package com.sivaprakash.fund.config;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sivaprakash.fund.dto.BeneficiaryResponseDTO;

@FeignClient(name = "beneficiary-service", url = "http://localhost:8084/api/v1/beneficiaries/beneficiaryId") 
public interface BeneficiaryClient {
    
    @GetMapping("/{beneficiaryId}")
    BeneficiaryResponseDTO getBeneficiaryByCustomerId(@PathVariable("beneficiaryId") Long beneficiaryId);
}
