package com.sivaprakash.auth_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sivaprakash.auth_service.dto.AuthResponseDTO;
import com.sivaprakash.auth_service.dto.LoginRequestDTO;
import com.sivaprakash.auth_service.dto.UserResponseDTO;
import com.sivaprakash.auth_service.service.UserService;
import com.sivaprakash.auth_service.util.JwtUtil;

@RestController
@RequestMapping("/")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        System.out.println("Login request received: " + loginRequest);

        try {
            UserResponseDTO user = userService.validateUser(loginRequest);

            if(user != null) {
                String token = jwtUtil.generateToken(user.getUsername());
                return ResponseEntity.ok(new AuthResponseDTO(token));
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Auth service is working!");
    }
}