package com.sivaprakash.user_service.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sivaprakash.user_service.dto.LoginRequestDTO;
import com.sivaprakash.user_service.dto.UserResponseDTO;
import com.sivaprakash.user_service.entity.User;
import com.sivaprakash.user_service.service.UserService;

import jakarta.persistence.criteria.ListJoin;
import java.util.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public List<User> sayHello() {
    	System.out.println("----------------getAllUsers-------------------------");
        return userService.getAllUsers();
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

	
    @GetMapping("/greatings")
    public List<User> greatings() {
    	System.out.println("----------------greatings-------------------------");
        return userService.getAllUsers();
    }

	@PostMapping("/validate")
    public ResponseEntity<UserResponseDTO> validateUser(@RequestBody LoginRequestDTO loginRequest) {
        User userData = userService.validateUser(loginRequest.getUsername());
        System.out.println("User controller validate : "+userData.getPassword());
        System.out.println("User controller validate encoder : "+passwordEncoder.encode(loginRequest.getPassword()));
        if (userData != null && passwordEncoder.matches(loginRequest.getPassword(), userData.getPassword())) {

            UserResponseDTO response = new UserResponseDTO(
                    userData.getUserId(), // You might want to store actual IDs
                    userData.getUsername(),
                    userData.getEmail(),
                    userData.getRole()
            );
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.notFound().build();
    }
	
	// OTP verification endpoint (Step 6)
	@PostMapping("/fetchUser")
	public ResponseEntity<?> getUserNameandPassword(@RequestParam String username, @RequestParam String passwrod) {
		System.out.println("getUserNameandPassword.........................");
		try {
			Optional<User> opt = userService.login(username, passwrod);
			System.out.println("getUserNameandPassword........................ is present." + opt.isPresent());
			return ResponseEntity.ok(opt.get()); // Verify OTP entered by the user
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
	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
		System.out.println("Login..................");
//        String email = loginRequest.getEmail();
//        String password = loginRequest.getPassword();

		System.out.println("Login..................");
		Optional<User> optionalUser = userService.login(email, password);
		System.out.println("Login service " + email + " : " + password);
		if (optionalUser.isPresent()) {
			System.out.println("Login.................." + optionalUser.get());
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

	// update user profile based on user id
	@PutMapping("/{userId}")
	public ResponseEntity<?> updateUserProfile(@PathVariable Long userId, @RequestBody User user) {
		try {
			return ResponseEntity.ok(userService.updateUserProfile(userId, user));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	// create a endpoint to get user by id and create a endpoint to delete user by
	// id
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


	// Login endpoint
	@PostMapping(value = "/getUserByName")
	public ResponseEntity<?> getUserByName(@RequestParam String username) {
		System.out.println("Login..................");
//        String email = loginRequest.getEmail();
//        String password = loginRequest.getPassword();

		System.out.println("Login..................");
		Optional<User> optionalUser = userService.getUserByName(username);
		System.out.println("Login service " + username);
		if (optionalUser.isPresent()) {
			System.out.println("Login.................." + optionalUser.get());
			return ResponseEntity.ok(optionalUser.get());
		} else {
			System.out.println("Controller Login..................Not found...");
			return ResponseEntity.status(401).body("Invalid credentials");
		}
	}

}
