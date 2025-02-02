package com.sivaprakash.auth_service.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sivaprakash.auth_service.dto.AuthResponseDTO;
import com.sivaprakash.auth_service.dto.CustomerResponseDTO;
import com.sivaprakash.auth_service.dto.LoginRequestDTO;
import com.sivaprakash.auth_service.dto.OtpValidateDTO;
import com.sivaprakash.auth_service.dto.RegisterRequestDTO;
import com.sivaprakash.auth_service.dto.UserResponseDTO;
import com.sivaprakash.auth_service.service.AuthService;
import com.sivaprakash.auth_service.util.JwtUtil;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        System.out.println("Login request received: " + loginRequest);

        try {
            UserResponseDTO user = authService.validateUser(loginRequest);
           // UserResponseDTO customer = authService.getCustomerByuserId(null);
            System.out.println("Auth UserResponseDTO :"+user);
            if(user != null) {
            	String customerId = authService.getCustomerByuserId(user.getUserId());
            	System.out.println("customerId 1 : "+customerId);
            	String token = jwtUtil.generateToken(user.getUsername(),user.getUserId(),user.getCustomerId());
                return ResponseEntity.ok(new AuthResponseDTO(token,user.getUserId(),user.getUsername(),customerId));
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO registerRequest) {
        System.out.println("Register request received: " + registerRequest);

        try {
            // Register the user
            UserResponseDTO user = authService.registerUser(registerRequest);
            System.out.println("Auth register : " + user);

            if (user != null) {
                // Send OTP to both mobile and email
                //authService.sendOtp(String.valueOf(user.getPhoneNumber()), user.getEmail());
                System.out.println("Registered otp :" + user.getOtp());

                // Create a JSON response as a Map
                Map<String, Object> response = new HashMap<>();
                response.put("message", "User registered successfully.");
                response.put("userId", user.getUserId());

                return ResponseEntity.ok(response);
            }

            // Failure response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "User registration failed."));
        } catch (Exception e) {
            e.printStackTrace();
            // Error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An error occurred during registration."));
        }
    }
    
    @PostMapping("/validate-otp")
    public ResponseEntity<?> validateOtp(@RequestBody OtpValidateDTO otpValidateRequest) {
        logger.info("OTP validation request received for ID: {}", otpValidateRequest.getUserId());

        try {
            // Fetch the user by ID
            UserResponseDTO user = authService.getUserById(otpValidateRequest.getUserId());
            logger.info("validateOtp UserResponseDTO from database user ",user);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }

            logger.info("user.getOtp() : ",user.getOtp());
            // Validate the OTP
            boolean isOtpValid = authService.validateOtp(String.valueOf(user.getOtp()), otpValidateRequest.getOtp());
            if (isOtpValid) {
                // Update user status
                user.setStatus("ACTIVE");
                boolean isUpdatedUser = authService.updateUser(user);
                
                if (isUpdatedUser) {
                	String customerId = authService.createCustomer(user.getUserId());
                	logger.info("Customer created :",customerId);
                	// impliment account using feign client
                //	boolean isAccountCreated = authService.createAccount(customerId);
                //	logger.info("isAccountCreated",isAccountCreated);
                    return ResponseEntity.ok("OTP validated successfully and user status updated to ACTIVE.");
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update user status.");
                }
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP.");
        } catch (Exception e) {
            logger.error("Error during OTP validation: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during OTP validation.");
        }
    }

}