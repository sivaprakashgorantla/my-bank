package com.sivaprakash.user.ment.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sivaprakash.user.ment.entity.User;
import com.sivaprakash.user.ment.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    //default endpoint
    @GetMapping("/")
    public String defaultEndpoint() {
        return "Welcome to User Management API";
    }
// get all users
    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        System.out.println("Get all users..................");
        return ResponseEntity.ok(userService.getAllUsers());
    }
    // Register endpoint (Step 1)
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            // Register the user and send OTP
            return ResponseEntity.ok(userService.registerUser(user));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // OTP verification endpoint (Step 6)
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestParam String username, @RequestParam String otp) {
        try {
            return ResponseEntity.ok(userService.verifyOtp(username, otp)); // Verify OTP entered by the user
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
    	System.out.println("Login..................");
    	Optional<User> optionalUser = userService.login(email, password);
    	System.out.println("Login service "+email +" : "+password);
    	if (optionalUser.isPresent()) {
    		System.out.println("Login.................."+optionalUser.get());
    		return ResponseEntity.ok(optionalUser.get());
        } else {
        	System.out.println("Controller Login..................Not found...");
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    // Update customer ID
    @PutMapping("/{userId}/update-customer-id")
    public ResponseEntity<?> updateCustomerId(@PathVariable Long userId, @RequestParam String customerId) {
        try {
            return ResponseEntity.ok(userService.updateCustomerId(userId, customerId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //update user profile based on user id
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUserProfile(@PathVariable Long userId, @RequestBody User user) {
        try {
            return ResponseEntity.ok(userService.updateUserProfile(userId, user));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    // create a endpoint to get user by id and create a endpoint to delete user by id
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(userService.getUserById(userId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long userId) {
        try {
            userService.deleteUserById(userId);
            return ResponseEntity.ok("User deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
     
}
