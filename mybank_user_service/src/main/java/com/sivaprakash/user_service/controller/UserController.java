package com.sivaprakash.user_service.controller;

import com.sivaprakash.user_service.dto.LoginRequestDTO;
import com.sivaprakash.user_service.dto.UserResponseDTO;
import com.sivaprakash.user_service.entity.User;
import com.sivaprakash.user_service.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User registerUser) {
        logger.info("Received registration request for user: {}", registerUser.getUsername());
        try {
            return ResponseEntity.ok(userService.registerUser(registerUser));
        } catch (IllegalArgumentException e) {
            logger.error("Registration failed for user: {}. Error: {}", registerUser.getUsername(), e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/validate")
    public ResponseEntity<UserResponseDTO> validateUser(@RequestBody LoginRequestDTO loginRequest) {
        logger.info("Validating user: {}", loginRequest.getUsername());

        User user = userService.validateUser(loginRequest.getUsername());
        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            Long customerId = userService.getCustomeByUserId(user.getUserId());
            UserResponseDTO response = new UserResponseDTO(
                    user.getUserId(),
                    user.getUsername(),
                    user.getFirstName(),
                    user.getLastName(),
                    null, // Exclude password
                    user.getEmail(),
                    customerId != null ? customerId.toString() : null,
                    user.getPhoneNumber(),
                    user.getStatus().toString()
            );
            logger.info("User validated successfully: {}", user.getUsername());
            return ResponseEntity.ok(response);
        }
        logger.warn("Validation failed for user: {}", loginRequest.getUsername());
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/fetchUser")
    public ResponseEntity<?> getUserNameAndPassword(@RequestParam String username, @RequestParam String password) {
        logger.info("Fetching user details for username: {}", username);
        try {
            Optional<User> opt = userService.login(username, password);
            if (opt.isPresent()) {
                logger.info("User fetched successfully: {}", username);
                return ResponseEntity.ok(opt.get());
            } else {
                logger.warn("User not found for username: {}", username);
                return ResponseEntity.status(404).body("User not found");
            }
        } catch (IllegalArgumentException e) {
            logger.error("Error fetching user: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestParam String username, @RequestParam String otp) {
        logger.info("Verifying OTP for username: {}", username);
        try {
            return ResponseEntity.ok(userService.verifyOtp(username, otp));
        } catch (IllegalArgumentException e) {
            logger.error("OTP verification failed for username: {}. Error: {}", username, e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUserProfile(@RequestBody User updateUser) {
        logger.info("Updating user profile for userId: {}", updateUser.getUserId());
        try {
            User updatedUser = userService.updateUser(updateUser);
            logger.info("User profile updated successfully for userId: {}", updatedUser.getUserId());
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to update user profile. Error: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
//
//    @GetMapping("/customer/{customerId}")
//    public ResponseEntity<?> getUserByCustomerId(@PathVariable String customerId) {
//        logger.info("Fetching user by customerId: {}", customerId);
//        try {
//            return ResponseEntity.ok(userService.getUserByCustomerId(customerId));
//        } catch (IllegalArgumentException e) {
//            logger.error("Failed to fetch user by customerId: {}. Error: {}", customerId, e.getMessage());
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        logger.info("Fetching user by userId: {}", userId);
        try {
            return ResponseEntity.ok(userService.getUserById(userId));
        } catch (IllegalArgumentException e) {
            logger.error("Failed to fetch user by userId: {}. Error: {}", userId, e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long userId) {
        logger.info("Deleting user with userId: {}", userId);
        try {
            userService.deleteUserById(userId);
            logger.info("User deleted successfully: {}", userId);
            return ResponseEntity.ok("User deleted successfully");
        } catch (IllegalArgumentException e) {
            logger.error("Failed to delete user with userId: {}. Error: {}", userId, e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/getUserByName")
    public ResponseEntity<?> getUserByName(@RequestParam String username) {
        logger.info("Fetching user by username: {}", username);
        Optional<User> optionalUser = userService.getUserByName(username);
        if (optionalUser.isPresent()) {
            logger.info("User fetched successfully: {}", username);
            return ResponseEntity.ok(optionalUser.get());
        } else {
            logger.warn("User not found for username: {}", username);
            return ResponseEntity.status(404).body("User not found");
        }
    }
}
