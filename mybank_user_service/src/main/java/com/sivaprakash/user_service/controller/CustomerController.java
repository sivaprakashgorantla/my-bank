package com.sivaprakash.user_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sivaprakash.user_service.dto.CustomerProfileDTO;
import com.sivaprakash.user_service.dto.CustomerProfileUpdateRequest;
import com.sivaprakash.user_service.dto.OtpValidationRequest;
import com.sivaprakash.user_service.service.CustomerService;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
	
	@Autowired
    private CustomerService customerService;
//
//    @Autowired
//    public CustomerController(CustomerService customerService) {
//        this.customerService = customerService;
//    }

    @GetMapping("/profile")
    public ResponseEntity<CustomerProfileDTO> getCustomerProfile(@RequestParam String username) {
        CustomerProfileDTO profile = customerService.getCustomerProfile(username);
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/profile/customer-id")
    public ResponseEntity<String> updateCustomerId(@RequestBody CustomerProfileUpdateRequest request) {
        customerService.updateCustomerId(request);
        return ResponseEntity.ok("OTP sent to registered mobile and email");
    }

    @PostMapping("/profile/validate-otp")
    public ResponseEntity<String> validateOtpAndUpdateProfile(@RequestBody OtpValidationRequest request) {
        customerService.validateOtpAndUpdateProfile(request);
        return ResponseEntity.ok("Profile updated successfully");
    }
}