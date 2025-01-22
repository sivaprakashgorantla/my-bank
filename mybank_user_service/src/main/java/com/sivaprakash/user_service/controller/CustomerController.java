package com.sivaprakash.user_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sivaprakash.user_service.dto.CustomerProfileDTO;
import com.sivaprakash.user_service.dto.CustomerProfileUpdateRequest;
import com.sivaprakash.user_service.service.CustomerService;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@PostMapping("/create-customer/{userId}")
	public ResponseEntity<?> createCustomer(@PathVariable Long userId) { // Change @RequestParam to @PathVariable
		System.out.println("CustomerController createCustomer : " + userId);
		try {
			// Register the user and send OTP
			return ResponseEntity.ok(customerService.createCustmer(userId));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

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
	
	@GetMapping("/customer-id/{userId}")
	public ResponseEntity<String> getCustomerIdByUserId(@PathVariable Long userId) {
	    try {
	        String customerId = customerService.getCustomerIdByUserId(userId);
	        return ResponseEntity.ok(customerId);
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body(e.getMessage());
	    }
	}


}