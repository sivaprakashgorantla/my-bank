package com.sivaprakash.user_service.service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	private RedisTemplate<String, String> redisTemplate;

	private static final int OTP_EXPIRATION_MINUTES = 30;

	@Transactional(readOnly = true)
	public CustomerProfileDTO getCustomerProfile(String username) {
		logger.info("Fetching profile for username: {}", username);
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		Customer customer = customerRepository.findByUser(user)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

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
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		Customer customer = customerRepository.findByUser(user)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

		// Update customer ID (will be confirmed after OTP validation)
		customer.setCustomerId(request.getCustomerId());
		customerRepository.save(customer);

		logger.info("Customer ID updated successfully for username: {}", request.getUsername());
	}

	@Transactional
	public void validateOtpAndUpdateProfile(OtpValidationRequest request) {
		logger.info("Validating OTP for username: {}", request.getUsername());
		User user = userRepository.findByUsername(request.getUsername())
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		Customer customer = customerRepository.findByUser(user)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

		customer.setProfileStatus(ProfileStatus.VERIFIED);
		customerRepository.save(customer);

		logger.info("OTP validated and profile updated for username: {}", request.getUsername());
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

	public Customer createCustmer(Long userId) {
		logger.info("Creating customer for user ID: {}", userId);
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

		// Create a new Customer and associate the fetched user
		Customer customer = new Customer();
		customer.setUser(user); // Set the user object in the customer
		Customer savedCustomer = customerRepository.save(customer);

		logger.info("Customer created successfully for user ID: {}", userId);
		return savedCustomer;
	}

	public void sendOtp(String phoneNumber, String email) {
		// Generate OTP
		String otp = generateOtp();

		// Store OTP in Redis with expiration
		redisTemplate.opsForValue().set("OTP_" + phoneNumber, otp, OTP_EXPIRATION_MINUTES, TimeUnit.MINUTES);

		// Mock sending OTP via email and SMS
		logger.info("Sending OTP '{}' to phone: {} and email: {}", otp, phoneNumber, email);

		// Use an actual email or SMS service to send the OTP here
	}

	public boolean validateOtp(String phoneNumber, String otp) {
		String storedOtp = redisTemplate.opsForValue().get("OTP_" + phoneNumber);

		if (storedOtp != null && storedOtp.equals(otp)) {
			// OTP is valid; remove from Redis
			redisTemplate.delete("OTP_" + phoneNumber);
			logger.info("OTP validated successfully for phone: {}", phoneNumber);
			return true;
		}

		logger.warn("Invalid OTP '{}' for phone: {}", otp, phoneNumber);
		return false;
	}

	private String generateOtp() {
		Random random = new Random();
		return String.format("%06d", random.nextInt(1000000)); // Generate a 6-digit OTP
	}

	@Transactional(readOnly = true)
	public Long getCustomerIdByUserId(Long userId) {
		logger.info("Fetching customer ID for user ID: {}", userId);
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

		// Fetch the customer associated with the user
		Customer customer = customerRepository.findByUser(user)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found for user ID: " + userId));

		// Return the customer ID
		logger.info("Customer ID retrieved successfully for user ID: {}", userId);
		return customer.getCustomerId();
	}

	public List<CustomerDTO> getAllCustomers() {
		logger.info("Fetching all customers...");
		List<Customer> customers = customerRepository.findAll();

		// Convert entities to DTOs
		List<CustomerDTO> customerDTOs = customers.stream().map(customer -> {
			User user = customer.getUser(); // Get the associated user
			if (user == null) {
				logger.warn("User is null for customer ID: {}", customer.getCustomerId());
				return null; // Return null if user is null
			}

			return new CustomerDTO(customer.getCustomerId(), user.getUserId(), // Assuming User entity has getUserId
																				// method
					customer.getProfileStatus(), customer.getCreatedAt(), customer.getUpdatedAt());
		}).filter(customerDTO -> customerDTO != null) // Remove null values if any
				.collect(Collectors.toList());

		logger.info("Fetched {} customers", customerDTOs.size());
		return customerDTOs;
	}
	public List<Long> getAllCustomersIds() {
	    logger.info("Fetching all customers...");

	    // Fetch all customers from the repository
	    List<Customer> customers = customerRepository.findAll();

	    // Extract customer IDs into a List<Long>
	    List<Long> customerIds = customers.stream()
	            .map(customer -> customer.getCustomerId())  // Extract customer ID
	            .collect(Collectors.toList());

	    logger.info("Fetched {} customer IDs", customerIds.size());
	    return customerIds;
	}

}
