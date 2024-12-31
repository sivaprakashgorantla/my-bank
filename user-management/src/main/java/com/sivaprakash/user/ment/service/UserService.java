package com.sivaprakash.user.ment.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sivaprakash.user.ment.entity.User;
import com.sivaprakash.user.ment.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

// get all users from the database
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
    // Simulate sending OTP via SMS and email (mocking for now)
    private void sendOtp(User user) {
        String otp = generateOtp();
        user.setOtp(otp); // Set OTP in the user's entity

        // Simulate sending OTP to email and mobile number
        System.out.println("Sending OTP: " + otp + " to email: " + user.getEmail() + " and mobile: " + user.getPhoneNumber());
        
        // TODO: Integrate real email and SMS service here (e.g., SendGrid for email, Twilio for SMS)
    }

    // Generate a random OTP (6 digits)
    private String generateOtp() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000)); // 6-digit OTP
    }

    // Register user and send OTP
    public User registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        
        User savedUser = userRepository.save(user);
        
        sendOtp(savedUser); // Send OTP after user is saved
        
        return savedUser;
    }

    // Verify OTP during registration
    public String verifyOtp(String username, String otp) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user.getOtp() == null || !user.getOtp().equals(otp)) {
            throw new IllegalArgumentException("Invalid OTP");
        }

        // Clear OTP after successful verification
        user.setOtp(null);
        userRepository.save(user);
        
        return "OTP verification successful";
    }

    // Login user with validation
    public Optional<User> login(String email, String password) {
    	System.out.println("Login service "+email +" : "+password);
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("UsernameSystem.out.println(\"Login..................\"+optionalUser.get()); cannot be null or empty");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        System.out.println("service login.");
        return userRepository.findByEmail(email)
                .filter(user -> user.getPasswordHash().equals(password));
    }
    // Update customer ID for the user

    public User updateCustomerId(Long userId, String customerId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            user.setCustomerId(customerId);
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update customer ID", e);
        }
    }

    // Get user by ID and create    to delete user by ID

    public User getUserById(Long Id   ) {
        return userRepository.findById(Id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    // update user profile based on user id
    public User updateUserProfile(Long userId, User user) {
        try {
            User existingUser = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPhoneNumber(user.getPhoneNumber());
            existingUser.setDateOfBirth(user.getDateOfBirth());
            existingUser.setAddress(user.getAddress());
            existingUser.setProfileUpdated("Y");
            return userRepository.save(existingUser);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update user profile", e);
        }
    }

    public void deleteUserById(Long userId) {
        try {
            userRepository.deleteById(userId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete user", e);
        }
    }
}

