package com.sivaprakash.auth.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sivaprakash.auth.service.CustomUserDetailsService;
import com.sivaprakash.auth.util.JwtUtil;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/authLogin")
    public Mono<Map<String, String>> authLogin(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        System.out.println("authLogin : "+username +" : "+password);
        return //userDetailsService.findByUsername(username)
        		customUserDetailsService.findByUsername(username).flatMap(userDetails -> {
        			System.out.println("AuthController : "+userDetails.getUsername()+" : "+userDetails.getPassword());

                        String token = jwtUtil.generateToken(userDetails.getUsername());
                        Map<String, String> response = new HashMap<>();
                        response.put("token", token);
                        return Mono.just(response);
                });
    }
}
