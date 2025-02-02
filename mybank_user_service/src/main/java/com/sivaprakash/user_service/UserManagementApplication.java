package com.sivaprakash.user_service;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@SpringBootApplication 
@EnableJpaRepositories(basePackages = "com.sivaprakash.user_service.repository")
@EnableDiscoveryClient
@CircuitBreaker(name = "userService")
@Retry(name = "userService")
@EnableFeignClients
class UserManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserManagementApplication.class, args);
    }
    
    
}
