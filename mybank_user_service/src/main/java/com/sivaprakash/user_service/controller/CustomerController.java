package com.sivaprakash.user_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sivaprakash.user_service.dto.CustomerProfileDTO;
import com.sivaprakash.user_service.dto.CustomerProfileUpdateRequest;
import com.sivaprakash.user_service.service.CustomerService;
import com.sivaprakash.user_service.dto.CustomerDTO; // Assuming CustomerDTO exists for customer details

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @PostMapping("/create-customer/{userId}")
    public ResponseEntity<?> createCustomer(@PathVariable Long userId) {
        logger.info("CustomerController createCustomer: userId={}", userId);
        try {
            return ResponseEntity.ok(customerService.createCustomer(userId));
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error in createCustomer: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getCustomerProfile(@RequestParam String username) {
        logger.info("Fetching profile for username={}", username);
        try {
            CustomerProfileDTO profile = customerService.getCustomerProfile(username);
            return ResponseEntity.ok(profile);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Profile not found for username={}", username);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profile not found");
        } catch (Exception e) {
            logger.error("Error fetching profile: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching customer profile");
        }
    }

    @PutMapping("/profile/customer-id")
    public ResponseEntity<?> updateCustomerId(@RequestBody CustomerProfileUpdateRequest request) {
        logger.info("Updating customer ID for request={}", request);
        try {
            customerService.updateCustomerId(request);
            return ResponseEntity.ok("OTP sent to registered mobile and email");
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid request data: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error while updating customer ID: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update customer ID");
        }
    }

    @GetMapping("/customer-id/{userId}")
    public ResponseEntity<?> getCustomerIdByUserId(@PathVariable Long userId) {
        logger.info("Fetching customer ID for userId={}", userId);
        try {
            Long customerId = customerService.getCustomerIdByUserId(userId);
            return ResponseEntity.ok(customerId);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid userId: {}", userId);
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error fetching customer ID: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving customer ID");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCustomers() {
        logger.info("Fetching all customers");
        try {
            List<CustomerDTO> customers = customerService.getAllCustomers();
            return ResponseEntity.ok(customers);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("No customers found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No customers found");
        } catch (Exception e) {
            logger.error("Error fetching all customers: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve customers");
        }
    }
}
