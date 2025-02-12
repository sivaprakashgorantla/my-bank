package com.sivaprakash.user_service.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sivaprakash.user_service.entity.Customer;
import com.sivaprakash.user_service.entity.User;
import com.sivaprakash.user_service.enums.ProfileStatus;
import com.sivaprakash.user_service.repository.UserRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	private static final String USER_SERVICE = "userService";

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// Fetch all users
	public List<User> getAllUsers() {
		logger.info("Fetching all users");
		return userRepository.findAll();
	}

	// Generate and save OTP in the database
	// Generate and save OTP in the database with exception handling
	public User generateAndSaveOtp(User user) {
		try {
			String otp = generateOtp();
			user.setOtp(otp);
			userRepository.save(user);
			logger.info("Generated OTP: {} for user: {}", otp, user.getUsername());
		} catch (Exception e) {
			logger.error("Failed to generate and save OTP for user: {}. Error: {}", user.getUsername(), e.getMessage());
		}
		return user;
	}

	// Generate a 6-digit OTP
	private String generateOtp() {
		Random random = new Random();
		return String.format("%06d", random.nextInt(1000000));
	}

	// Register a new user
	public User registerUser(User user) {
		logger.info("Registering user: {}", user.getUsername());

		if (userRepository.findByEmail(user.getEmail()).isPresent()) {
			logger.error("Registration failed. Email {} already exists.", user.getEmail());
			throw new IllegalArgumentException("Email already exists");
		}
		if (userRepository.findByUsername(user.getUsername()).isPresent()) {
			logger.error("Registration failed. Username {} already exists.", user.getUsername());
			throw new IllegalArgumentException("Username already exists");
		}

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User savedUser = userRepository.save(user);

		User generateAndSaveOtp = generateAndSaveOtp(savedUser);
		logger.info("User registered successfully: {}", savedUser.getUsername());
		return generateAndSaveOtp;
	}

	// Verify OTP // this method while registration
	public String verifyOtp(String username, String otp) {
		logger.info("Verifying OTP for user: {}", username);

		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new IllegalArgumentException("User not found"));

		if (user.getOtp() == null || !user.getOtp().equals(otp)) {
			logger.error("OTP verification failed for user: {}. Invalid OTP.", username);
			throw new IllegalArgumentException("Invalid OTP");
		}

		user.setOtp(null);
		userRepository.save(user);
		logger.info("OTP verified successfully for user: {}", username);
		return "OTP verification successful";
	}

	// this method profile update
	public String verifyOtp(Long userId, String otp) {
		logger.info("Verifying OTP for user: {}", userId);

		User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

		if (user.getOtp() == null || !user.getOtp().equals(otp)) {
			logger.error("OTP verification failed for user: {}. Invalid OTP.", userId);
			throw new IllegalArgumentException("Invalid OTP");
		}

		user.setOtp(null);
		userRepository.save(user);
		logger.info("OTP verified successfully for user: {}", userId);
		return "OTP verification successful";
	}

	// Login user
	public Optional<User> login(String email, String password) {
		logger.info("Logging in user with email: {}", email);

		Optional<User> dbUser = userRepository.findByEmail(email);
		if (dbUser.isPresent() && passwordEncoder.matches(password, dbUser.get().getPassword())) {
			logger.info("Login successful for user: {}", email);
			return dbUser;
		} else {
			logger.warn("Login failed for user: {}. Invalid credentials.", email);
			return Optional.empty();
		}
	}

	// Update user information

	public User updateUser(User updateUser) {
		logger.info("Updating user with ID: {}", updateUser.getUserId());

		User user = userRepository.findById(updateUser.getUserId()).orElseThrow(() -> {
			logger.error("User not found with ID: {}", updateUser.getUserId());
			return new IllegalArgumentException("User not found");
		});

		// OTP validation
		if (user.getOtp() == null || !user.getOtp().equals(updateUser.getOtp())) {
			logger.error("OTP verification failed for user: {}. Invalid OTP.", updateUser.getUserId());
			throw new IllegalArgumentException("Invalid OTP");
		}
		
		

		// Update only non-masked fields
		if (!isMasked(updateUser.getEmail())) {
			user.setEmail(updateUser.getEmail());
		}

		if (!isMasked(updateUser.getPhoneNumber())) {
			user.setPhoneNumber(updateUser.getPhoneNumber());
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		// Update other modifiable fields
		user.setFirstName(updateUser.getFirstName());
		user.setLastName(updateUser.getLastName());
		user.setAddress(updateUser.getAddress());
		user.setUpdatedAt(LocalDateTime.now());
		user.setDateOfBirth(formatter !=null ? updateUser.getDateOfBirth():null);
		user.setRole(updateUser.getRole());
		user.setStatus(updateUser.getStatus());

		// Clear OTP after successful validation
		user.setOtp(null);

		// Save updated user (JPA will update, not insert)
		User updatedUser = userRepository.save(user);
//
//		try {
//			Customer customer = customerService.getCustomerByUserId(updatedUser.getUserId());
//			customer.setProfileStatus(ProfileStatus.VERIFIED);
//			customerService.updateCustomer(customer);
//			logger.info("Customer updated successfully for user ID: {}", updatedUser.getUserId());
//		} catch (Exception e) {
//			logger.error("Failed to update customer for user ID: {}. Error: {}", updatedUser.getUserId(),
//					e.getMessage());
//			throw new RuntimeException("Failed to update customer profile", e);
//		}
//
		logger.info("User updated successfully with ID: {}", updatedUser.getUserId());
		return updatedUser;
	}

	// Helper method to check if a value is masked
	private boolean isMasked(String value) {
		return value != null && (value.contains("*") || value.matches(".*\\*.*"));
	}


	// Delete user by ID
	public void deleteUserById(Long userId) {
		logger.info("Deleting user with ID: {}", userId);
		userRepository.deleteById(userId);
		logger.info("User deleted successfully with ID: {}", userId);
	}

	// Fetch user by username
	public Optional<User> getUserByName(String username) {
		logger.info("Fetching user by username: {}", username);
		return userRepository.findByUsername(username);
	}

	// Validate user by username
	public User validateUser(String username) {
		logger.info("Validating user by username: {}", username);
		return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
	}

	// Fetch customer by user ID
	public Long getCustomeByUserId(Long userId) {
		logger.info("Fetching customer by user ID: {}", userId);
		try {
			Long customerId = customerService.getCustomerIdByUserId(userId);
			logger.info("Customer fetched successfully: {} for user ID: {}", customerId, userId);
			return customerId;
		} catch (Exception e) {
			logger.error("Error fetching customer for user ID: {}. Error: {}", userId, e.getMessage());
			throw new RuntimeException("Failed to fetch customer for user ID: " + userId, e);
		}
	}

	// Method to mask email
	public String maskEmail(String email) {
		if (email == null || !email.contains("@"))
			return "*****";

		String[] parts = email.split("@");
		String firstPart = parts[0];
		String domain = parts[1];

		if (firstPart.length() <= 2) {
			return firstPart.charAt(0) + "*****@" + domain;
		}

		return firstPart.charAt(0) + "*****" + firstPart.charAt(firstPart.length() - 1) + "@" + domain;
	}

	// Method to mask phone number
	public String maskPhoneNumber(String phoneNumber) {
		if (phoneNumber == null || phoneNumber.length() < 4)
			return "*****";

		return "*******" + phoneNumber.substring(phoneNumber.length() - 4);
	}

	@CircuitBreaker(name = USER_SERVICE, fallbackMethod = "fallbackGetUserById")
	public User getUserById(Long userId) {
	    logger.info("User Service Fetching user by ID: {}", userId);
	    
//	    // Simulate failure for circuit breaker testing
//	    if (Math.random() < 0.5) {
//	        throw new RuntimeException("Simulated failure");
//	    }

	    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("User not found"));
	    logger.info("User Service Fetching user by ID: {}", user);
	    return user;
	}


	public User fallbackGetUserById(Long userId, Throwable throwable) {
	    logger.warn("Fallback triggered for getUserById. Reason: {}", throwable.getMessage());
	    return new User(userId, "Fallback User", "N/A", "fallback@example.com", "0000000000");
	}
	
	
}
