package com.sivaprakash.user_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.sivaprakash.user_service.dto.CustomerDTO; // Assuming CustomerDTO exists for customer details

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

	// Logger instance
	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	private CustomerService customerService;

	@PostMapping("/create-customer/{userId}")
	public ResponseEntity<?> createCustomer(@PathVariable Long userId) { // Change @RequestParam to @PathVariable
		logger.info("CustomerController createCustomer: userId={}", userId);
		try {
			// Register the user and send OTP
			return ResponseEntity.ok(customerService.createCustmer(userId));
		} catch (IllegalArgumentException e) {
			logger.error("Error creating customer: {}", e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/profile")
	public ResponseEntity<CustomerProfileDTO> getCustomerProfile(@RequestParam String username) {
		logger.info("Fetching profile for username={}", username);
		CustomerProfileDTO profile = customerService.getCustomerProfile(username);
		return ResponseEntity.ok(profile);
	}

	@PutMapping("/profile/customer-id")
	public ResponseEntity<String> updateCustomerId(@RequestBody CustomerProfileUpdateRequest request) {
		logger.info("Updating customer ID for request={}", request);
		customerService.updateCustomerId(request);
		return ResponseEntity.ok("OTP sent to registered mobile and email");
	}

	@GetMapping("/customer-id/{userId}")
	public ResponseEntity<?> getCustomerIdByUserId(@PathVariable Long userId) {
		logger.info("Fetching customer ID for userId={}", userId);
		try {
			Long customerId = customerService.getCustomerIdByUserId(userId);
			return ResponseEntity.ok(customerId); // Return Long in success case
		} catch (IllegalArgumentException e) {
			logger.error("Error fetching customer ID: {}", e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage()); // Return String in error case
		}
	}

	// New method to fetch all customers
	@GetMapping("/all")
	public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
		logger.info("Fetching all customers");
		try {
			List<CustomerDTO> customers = customerService.getAllCustomers();
			return ResponseEntity.ok(customers);
		} catch (Exception e) {
			logger.error("Error fetching all customers: {}", e.getMessage());
			return ResponseEntity.status(500).body(null); // Internal server error
		}
	}
}
