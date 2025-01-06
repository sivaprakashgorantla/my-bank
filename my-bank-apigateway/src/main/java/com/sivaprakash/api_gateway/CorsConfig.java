package com.sivaprakash.api_gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Allow credentials (cookies, etc.)
        config.setAllowCredentials(true);
        
        // Angular frontend
        config.addAllowedOrigin("http://localhost:4200");
        
        // Allow all headers
        config.addAllowedHeader("*");
        
        // Allow all HTTP methods (GET, POST, PUT, DELETE, etc.)
        config.addAllowedMethod("*");

        // Apply CORS settings to all endpoints
        source.registerCorsConfiguration("/**", config);
        
        return new CorsWebFilter(source);
    }
    
    
}