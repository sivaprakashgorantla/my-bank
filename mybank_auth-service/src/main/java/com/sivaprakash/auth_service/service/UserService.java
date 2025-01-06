package com.sivaprakash.auth_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.sivaprakash.auth_service.dto.LoginRequestDTO;
import com.sivaprakash.auth_service.dto.UserResponseDTO;

@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;

    public UserResponseDTO validateUser(LoginRequestDTO loginRequest) {
        String url = "http://localhost:8081/api/v1/users/validate";
        System.out.println("user service : validateUser"+loginRequest.getUsername());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        UserResponseDTO userResponseDTO = null;
		try {
			HttpEntity<LoginRequestDTO> request = new HttpEntity<>(loginRequest, headers);
			userResponseDTO = restTemplate.postForObject(url, request, UserResponseDTO.class);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

        return userResponseDTO;
    }
}