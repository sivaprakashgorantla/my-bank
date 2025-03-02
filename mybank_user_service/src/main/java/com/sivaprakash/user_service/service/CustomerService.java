package com.sivaprakash.user_service.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sivaprakash.user_service.client.AccountFeignClient;
import com.sivaprakash.user_service.dto.CustomerDTO;
import com.sivaprakash.user_service.dto.CustomerProfileDTO;
import com.sivaprakash.user_service.dto.CustomerProfileUpdateRequest;
import com.sivaprakash.user_service.dto.OtpValidationRequest;
import com.sivaprakash.user_service.entity.Customer;
import com.sivaprakash.user_service.entity.User;
import com.sivaprakash.user_service.enums.ProfileStatus;
import com.sivaprakash.user_service.exception.ResourceNotFoundException;
import com.sivaprakash.user_service.repository.CustomerRepository;
import com.sivaprakash.user_service.repository.UserRepository;

@Service
public class CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountFeignClient accountFeignClient;
    
    private static final int OTP_EXPIRATION_MINUTES = 30;

    @Transactional(readOnly = true)
    public CustomerProfileDTO getCustomerProfile(String username) {
        logger.info("Fetching profile for username: {}", username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        Customer customer = customerRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for username: " + username));

        CustomerProfileDTO dto = new CustomerProfileDTO();
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setMobileNo(maskMobileNumber(user.getPhoneNumber()));
        dto.setEmailId(maskEmail(user.getEmail()));
        dto.setCustomerId(customer.getCustomerId());
        dto.setProfileStatus(customer.getProfileStatus());

        logger.info("Profile fetched successfully for user: {}", username);
        return dto;
    }

    @Transactional
    public void updateCustomerId(CustomerProfileUpdateRequest request) {
        logger.info("Updating customer ID for username: {}", request.getUsername());
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + request.getUsername()));

        Customer customer = customerRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for username: " + request.getUsername()));

        customer.setCustomerId(request.getCustomerId());
        customerRepository.save(customer);

        logger.info("Customer ID updated successfully for username: {}", request.getUsername());
    }

    @Transactional
    public void validateOtpAndUpdateProfile(OtpValidationRequest request) {
        logger.info("Validating OTP for username: {}", request.getUsername());
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + request.getUsername()));

        Customer customer = customerRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for username: " + request.getUsername()));

        customer.setProfileStatus(ProfileStatus.VERIFIED);
        customerRepository.save(customer);

        logger.info("OTP validated and profile updated for username: {}", request.getUsername());
    }

    @Transactional(readOnly = true)
    public CustomerProfileDTO getCustomerDetailsById(Long customerId) {
        logger.info("Fetching customer details for customer ID: {}", customerId);

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));

        User user = customer.getUser();
        if (user == null) {
            logger.warn("User not found for customer ID: {}", customerId);
            throw new ResourceNotFoundException("User not found for customer ID: " + customerId);
        }

        CustomerProfileDTO dto = new CustomerProfileDTO();
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setMobileNo(maskMobileNumber(user.getPhoneNumber()));
        dto.setEmailId(maskEmail(user.getEmail()));
        dto.setCustomerId(customer.getCustomerId());
        dto.setProfileStatus(customer.getProfileStatus());

        logger.info("Customer details fetched successfully for customer ID: {}", customerId);
        return dto;
    }

    private String maskMobileNumber(String mobileNo) {
        return "XXXXXX" + mobileNo.substring(mobileNo.length() - 4);
    }

    private String maskEmail(String email) {
        String[] parts = email.split("@");
        String name = parts[0];
        String maskedName = name.substring(0, 2) + "XXXX" + name.substring(name.length() - 2);
        return maskedName + "@" + parts[1];
    }

    public Customer createCustomer(Long userId) {
        logger.info("Creating customer for user ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        Customer customer = new Customer();
        customer.setUser(user);
        Customer savedCustomer = customerRepository.save(customer);
        
        boolean flag = accountFeignClient.createAccount(savedCustomer.getCustomerId());
        logger.info("Account  created successfully for user ID: {}", flag);
        if(!flag) {
        	System.out.println("flag :"+flag);
        }
        
        logger.info("Customer created successfully for user ID: {}", userId);
        return savedCustomer;
    }

    @Transactional(readOnly = true)
    public Long getCustomerIdByUserId(Long userId) {
        logger.info("Fetching customer ID for user ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        Customer customer = customerRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for user ID: " + userId));

        logger.info("Customer ID retrieved successfully for user ID: {}", userId);
        return customer.getCustomerId();
    }

    public List<CustomerDTO> getAllCustomers() {
        logger.info("Fetching all customers...");
        List<Customer> customers = customerRepository.findAll();

        if (customers.isEmpty()) {
            logger.warn("No customers found.");
            throw new ResourceNotFoundException("No customers found in the system.");
        }

        List<CustomerDTO> customerDTOs = customers.stream().map(customer -> {
            User user = customer.getUser();
            if (user == null) {
                logger.warn("User is null for customer ID: {}", customer.getCustomerId());
                return null;
            }
            return new CustomerDTO(customer.getCustomerId(), user.getUserId(), customer.getProfileStatus(), customer.getCreatedAt(), customer.getUpdatedAt());
        }).filter(customerDTO -> customerDTO != null).collect(Collectors.toList());

        logger.info("Fetched {} customers", customerDTOs.size());
        return customerDTOs;
    }

    public List<Long> getAllCustomersIds() {
        logger.info("Fetching all customer IDs...");
        List<Customer> customers = customerRepository.findAll();

        if (customers.isEmpty()) {
            logger.warn("No customers found.");
            throw new ResourceNotFoundException("No customers found in the system.");
        }

        List<Long> customerIds = customers.stream()
                .map(Customer::getCustomerId)
                .collect(Collectors.toList());

        logger.info("Fetched {} customer IDs", customerIds.size());
        return customerIds;
    }

    public Customer getCustomerByUserId(Long userId) {
        logger.info("Fetching customer by user ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        Customer customer = customerRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for user ID: " + userId));

        logger.info("Customer ID {} found for user ID: {}", customer.getCustomerId(), userId);
        return customer;
    }

    public Customer updateCustomer(Customer customer) {
        try {
            logger.info("Updating customer with ID: {}", customer.getCustomerId());
            Customer existingCustomer = customerRepository.findById(customer.getCustomerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customer.getCustomerId()));

            BeanUtils.copyProperties(customer, existingCustomer, "customerId", "user");

            Customer updatedCustomer = customerRepository.save(existingCustomer);
            logger.info("Customer updated successfully with ID: {}", updatedCustomer.getCustomerId());

            return updatedCustomer;
        } catch (DataAccessException e) {
            logger.error("Database error while updating customer with ID: {}. Error: {}", customer.getCustomerId(), e.getMessage());
            throw new RuntimeException("Database error occurred while updating customer", e);
        } catch (Exception e) {
            logger.error("Unexpected error while updating customer with ID: {}. Error: {}", customer.getCustomerId(), e.getMessage());
            throw new RuntimeException("An unexpected error occurred while updating customer", e);
        }
    }
}
