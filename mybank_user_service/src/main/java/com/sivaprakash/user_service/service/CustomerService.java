package com.sivaprakash.user_service.service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

		return dto;
	}

	@Transactional
	public void updateCustomerId(CustomerProfileUpdateRequest request) {
		User user = userRepository.findByUsername(request.getUsername())
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		Customer customer = customerRepository.findByUser(user)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found"));


		// Update customer ID (will be confirmed after OTP validation)
		customer.setCustomerId(request.getCustomerId());
		customerRepository.save(customer);
	}

	@Transactional
	public void validateOtpAndUpdateProfile(OtpValidationRequest request) {
		User user = userRepository.findByUsername(request.getUsername())
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		Customer customer = customerRepository.findByUser(user)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

		customer.setProfileStatus(ProfileStatus.VERIFIED);
		customerRepository.save(customer);
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
	    // Fetch the user from the database to ensure it exists
	    User user = userRepository.findById(userId)
	            .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

	    // Create a new Customer and associate the fetched user
	    Customer customer = new Customer();
	    customer.setUser(user); // Set the user object in the customer
	    return customerRepository.save(customer);
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
	public String getCustomerIdByUserId(Long userId) {
	    // Fetch the user by ID to ensure it exists
	    User user = userRepository.findById(userId)
	            .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

	    // Fetch the customer associated with the user
	    Customer customer = customerRepository.findByUser(user)
	            .orElseThrow(() -> new ResourceNotFoundException("Customer not found for user ID: " + userId));

	    // Return the customer ID
	    return customer.getCustomerId();
	}


}