package com.sivaprakash.auth_service.service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.sivaprakash.auth_service.dto.CustomerResponseDTO;
import com.sivaprakash.auth_service.dto.LoginRequestDTO;
import com.sivaprakash.auth_service.dto.RegisterRequestDTO;
import com.sivaprakash.auth_service.dto.UserResponseDTO;

@Service
public class AuthService {

	private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	private static final int OTP_EXPIRATION_MINUTES = 30;

	public UserResponseDTO validateUser(LoginRequestDTO loginRequest) {
		String url = "http://localhost:8081/api/v1/users/validate";
		logger.info("Validating user: {}", loginRequest.getUsername());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		UserResponseDTO userResponseDTO = null;
		try {
			HttpEntity<LoginRequestDTO> request = new HttpEntity<>(loginRequest, headers);
			userResponseDTO = restTemplate.postForObject(url, request, UserResponseDTO.class);
			System.out.println("userResponseDTO in login  :"+userResponseDTO);
		} catch (RestClientException e) {
			logger.error("Error validating user: {}", e.getMessage(), e);
		}

		return userResponseDTO;
	}

	public UserResponseDTO registerUser(RegisterRequestDTO registerRequest) {
		String url = "http://localhost:8081/api/v1/users/register";
		logger.info("Registering user: {}", registerRequest.getUsername());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		UserResponseDTO userResponseDTO = null;
		try {
			HttpEntity<RegisterRequestDTO> request = new HttpEntity<>(registerRequest, headers);
			userResponseDTO = restTemplate.postForObject(url, request, UserResponseDTO.class);
		} catch (RestClientException e) {
			logger.error("Error registering user: {}", e.getMessage(), e);
		}

		return userResponseDTO;
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

	public UserResponseDTO getUserById(Long id) {
	    String url = "http://localhost:8081/api/v1/users/" + id; // Append ID to the URL
	    logger.info("Fetching user by ID: {}", id);

	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON); // Optional for GET

	    UserResponseDTO userResponseDTO = null;

	    try {
	        HttpEntity<Void> request = new HttpEntity<>(headers);

	        ResponseEntity<UserResponseDTO> response = restTemplate.exchange(
	            url,
	            HttpMethod.GET,
	            request,
	            UserResponseDTO.class
	        );

	        // Ensure response body is assigned
	        userResponseDTO = response.getBody();
	    } catch (RestClientException e) {
	        logger.error("Error fetching user by ID: {}", e.getMessage(), e);
	    }

	    return userResponseDTO;
	}

	public String getCustomerByuserId(Long id) {
	    String url = "http://localhost:8081/api/v1/customers/customer-id/" + id; // Append ID to the URL
	    logger.info("getCustomerByuserId user by ID: {}", id);

	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON); // Optional for GET

	    String responseBody = null;

	    try {
	        HttpEntity<Void> request = new HttpEntity<>(headers);

	        // Fetch response as a String
	        ResponseEntity<String> response = restTemplate.exchange(
	            url,
	            HttpMethod.GET,
	            request,
	            String.class
	        );

	        responseBody = response.getBody(); // Assign raw JSON response
	        logger.info("Response body: {}", responseBody);
	    } catch (RestClientException e) {
	        logger.error("Error in getCustomerByuserId for ID {}: {}", id, e.getMessage(), e);
	    }

	    return responseBody; // Return the raw JSON response as String
	}
	

	public boolean updateUser(UserResponseDTO user) {
	    String url = "http://localhost:8081/api/v1/users/"+user.getUserId(); // Adjust the endpoint as needed
	    logger.info("Updating user with ID: {}", user.getUserId());

	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);

	    try {
	        HttpEntity<UserResponseDTO> request = new HttpEntity<>(user, headers);
	        restTemplate.put(url, request); // Use PUT method for updates
	        return true;
	    } catch (RestClientException e) {
	        logger.error("Error updating user: {}", e.getMessage(), e);
	        return false;
	    }
	}

	public boolean createCustomer(Long userId) {
	    String url = "http://localhost:8081/api/v1/customers/create-customer/" + userId; // Adjust the endpoint if necessary
	    logger.info("createCustomer in service : {}", userId);

	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON); // Content type as JSON

	    try {
	        // Use POST method with RestTemplate
	        HttpEntity<Void> request = new HttpEntity<>(headers); // No request body is needed here
	        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

	        // Log response and return success
	        logger.info("Customer creation response: {}", response.getBody());
	        return response.getStatusCode().is2xxSuccessful();
	    } catch (RestClientException e) {
	        logger.error("Error creating customer: {}", e.getMessage(), e);
	        return false;
	    }
	}

}
