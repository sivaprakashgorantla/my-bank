package com.sivaprakash.auth.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.sivaprakash.auth.CustomerProfileDTO;
import reactor.core.publisher.Mono;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class CustomUserDetailsService implements ReactiveUserDetailsService{

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ReactiveAuthenticationManager authenticationManager;
	
	@Autowired
	private ReactiveUserDetailsService reactiveUserDetailsService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Value("${user.service.url}")
	private String userServiceUrl;

	public Mono<UserDetails> loadUserByEmailAndPassword(String email, String rawPassword) {
		System.out.println("loadUserByEmailAndPassword..............");

		// Prepare form-data payload
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("email", email);
		formData.add("password", rawPassword);

		// Set headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        headers.setBearerAuth("your-token");

		// Build the request entity
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, headers);

		try {
			// Make the POST request
			ResponseEntity<CustomerProfileDTO> responseEntity = restTemplate.postForEntity(userServiceUrl,
					requestEntity, CustomerProfileDTO.class);

			// Handle the response
			CustomerProfileDTO profile = responseEntity.getBody();

			if (profile != null) {
				String encryptedPassword = profile.getPassword(); // Encrypted password from user service
				System.out.println("encryptedPassword :" + encryptedPassword + " : " + rawPassword);
				// Validate password
				System.out.println(rawPassword + " : " + encryptedPassword);

				//reactiveUserDetailsService.
				
				Mono<Authentication> authenticate = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(profile.getUsername(), rawPassword));
				System.out.println("UsernamePasswordAuthenticationToken "+authenticate);
				// Return UserDetails if authentication is successful
				return Mono.just(org.springframework.security.core.userdetails.User.builder()
						.username(profile.getUsername()).password(rawPassword).roles(profile.getRole()).build());
			}

			throw new UsernameNotFoundException("User not found");

		} catch (Exception e) {
			System.out.println("loadUserByEmailAndPassword....ERROR.........." + e.getMessage());
			throw new RuntimeException("Error checking user profile status", e);
		}
	}

	@Override
	public Mono<UserDetails> findByUsername(String username) {
		// TODO Auto-generated method stub
		System.out.println("loadUserByEmailAndPassword..............");

		// Prepare form-data payload
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("username", username);
		
		// Set headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        headers.setBearerAuth("your-token");

		// Build the request entity
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, headers);

		try {
			// Make the POST request
			ResponseEntity<CustomerProfileDTO> responseEntity = restTemplate.postForEntity(userServiceUrl,
					requestEntity, CustomerProfileDTO.class);

			// Handle the response
			CustomerProfileDTO profile = responseEntity.getBody();

			if (profile != null) {
				String encryptedPassword = profile.getPassword(); // Encrypted password from user service
				System.out.println("encryptedPassword :" + encryptedPassword + " : " + profile.getPassword());
				// Validate password

				//reactiveUserDetailsService.
				
				Mono<Authentication> authenticate = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(profile.getUsername(), profile.getPassword()));
				System.out.println("UsernamePasswordAuthenticationToken "+authenticate);
				// Return UserDetails if authentication is successful
				return Mono.just(org.springframework.security.core.userdetails.User.builder()
						.username(profile.getUsername()).password(profile.getPassword()).roles(profile.getRole()).build());
			}

			throw new UsernameNotFoundException("User not found");

		} catch (Exception e) {
			System.out.println("loadUserByEmailAndPassword....ERROR.........." + e.getMessage());
			throw new RuntimeException("Error checking user profile status", e);
		}

	}

}
