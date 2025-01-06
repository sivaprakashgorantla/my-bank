package com.sivaprakash.auth_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sivaprakash.auth_service.dto.LoginRequestDTO;
import com.sivaprakash.auth_service.dto.UserResponseDTO;

@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;

    public UserResponseDTO validateUser(LoginRequestDTO loginRequest) {
        String url = "http://localhost:9820/users/validate";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LoginRequestDTO> request = new HttpEntity<>(loginRequest, headers);

        return restTemplate.postForObject(url, request, UserResponseDTO.class);
    }
}