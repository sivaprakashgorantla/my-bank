package com.sivaprakash.user_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.servlet.http.HttpServletResponse;

//@Configuration
//@EnableWebSecurity
public class WebSecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth ->
//                        auth.requestMatchers("/users/validate", "/users/test", "/demo").permitAll()
//                                .anyRequest().authenticated()
//                )
//                .sessionManagement(session ->
//                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                .exceptionHandling(handling ->
//                        handling.authenticationEntryPoint((request, response, authException) -> {
//                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
//                        })
//                );
//
//        return http.build();
//    }
}