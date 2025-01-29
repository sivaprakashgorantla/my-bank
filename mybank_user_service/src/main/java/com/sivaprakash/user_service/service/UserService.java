package com.sivaprakash.user_service.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sivaprakash.user_service.entity.Customer;
import com.sivaprakash.user_service.entity.User;
import com.sivaprakash.user_service.enums.ProfileStatus;
import com.sivaprakash.user_service.repository.UserRepository;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

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
    private User generateAndSaveOtp(User user) {
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

    // Verify OTP
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

        User user = userRepository.findById(updateUser.getUserId())
                .orElseThrow(() -> {
                    logger.error("User not found with ID: {}", updateUser.getUserId());
                    return new IllegalArgumentException("User not found");
                });

        BeanUtils.copyProperties(updateUser, user, "userId", "password");
        user.setUpdatedAt(LocalDateTime.now());
        User updatedUser = userRepository.save(user);

        try {
            Customer customer = customerService.getCustomerByUserId(updatedUser.getUserId());
            customer.setProfileStatus(ProfileStatus.VERIFIED);
            customerService.updateCustomer(customer);
            logger.info("Customer updated successfully for user ID: {}", updatedUser.getUserId());
        } catch (Exception e) {
            logger.error("Failed to update customer for user ID: {}. Error: {}", updatedUser.getUserId(), e.getMessage());
            throw new RuntimeException("Failed to update customer profile", e);
        }

        logger.info("User updated successfully with ID: {}", updatedUser.getUserId());
        return updatedUser;
    }

    // Fetch user by ID
    public User getUserById(Long id) {
        logger.info("Fetching user by ID: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
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
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Create a customer for a user
    public boolean createCustomer(Long userId) {
        logger.info("Creating customer for user ID: {}", userId);
        try {
            Customer customer = customerService.createCustomer(userId);
            logger.info("Customer created successfully for user ID: {}", userId);
            return customer != null;
        } catch (Exception e) {
            logger.error("Failed to create customer for user ID: {}. Error: {}", userId, e.getMessage());
            return false;
        }
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
        if (email == null || !email.contains("@")) return "*****";
        
        String[] parts = email.split("@");
        String firstPart = parts[0];
        String domain = parts[1];

        if (firstPart.length() <= 2) {
            return firstPart.charAt(0) + "*****@" + domain;
        }

        return firstPart.charAt(0) + "*****" + firstPart.charAt(firstPart.length() - 1) + "@" + domain;
    }

    // Method to mask phone number
    public  String maskPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() < 4) return "*****";
        
        return "*******" + phoneNumber.substring(phoneNumber.length() - 4);
    }


}
