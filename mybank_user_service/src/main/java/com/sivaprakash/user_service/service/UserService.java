package com.sivaprakash.user_service.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sivaprakash.user_service.entity.User;
import com.sivaprakash.user_service.repository.UserRepository;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
// get all users from the database
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
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
    	System.out.println("registerUser Service : "+user);
    	
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
        
        Optional<User> dbUser  = userRepository.findByEmail(email);
        if(dbUser.isPresent() ) {
        	System.out.println("service login Details : ");
        }else {
        	System.out.println("service NO.................. login Details : ");
        }
        return userRepository.findByEmail(email);
    }
    // Update customer ID for the user

    public User updateUser(User updateUser) {
        try {
            User user = userRepository.findById(updateUser.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            BeanUtils.copyProperties(updateUser, user);
            System.out.println("updated user before save : "+user);
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

    public User getUserByCustomerId(String customerId) {
        return userRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
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

    
    public Optional<User> getUserByName(String username) {
    	System.out.println("getUserName service "+username);
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("UsernameSystem.out.println(\"Login..................\"+optionalUser.get()); cannot be null or empty");
        }
        
        Optional<User> dbUser  = userRepository.findByUsername(username);
        if(dbUser.isPresent() ) {
        	System.out.println("service login Details : ");
        }else {
        	System.out.println("service NO.................. login Details : ");
        }
        return userRepository.findByEmail(username);
    }
    
    public User validateUser(String username) {
    	Optional<User> findByUsername = userRepository.findByUsername(username);
    	
    	if(findByUsername.isPresent()) {
    		System.out.println(findByUsername.get().getPassword());
    		return findByUsername.get();
    	}else {
    		 throw new RuntimeException("Failed to get user");
    	}
    		
    }
}

