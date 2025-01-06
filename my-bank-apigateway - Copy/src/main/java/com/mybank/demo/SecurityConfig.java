package com.mybank.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(exchanges -> exchanges
                .pathMatchers("/api/**").permitAll()
                .anyExchange().authenticated())
            .build();
    }
}
//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//        http
//            .csrf().disable() // Disable CSRF for stateless APIs
//            .authorizeExchange(exchange -> exchange
//                .pathMatchers("/public/**").permitAll()
//                .anyExchange().authenticated()
//            )
//            .httpBasic() // Enable HTTP Basic Authentication
//            .and()
//            .formLogin(); // Optional: Enable Form-based Authentication
//
//        return http.build();
//    }
