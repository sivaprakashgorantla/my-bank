package com.sivaprakash.account.service.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
	@LoadBalanced  // Add this annotation
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate template = new RestTemplate();
        
        // Configure timeout
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);
        
        template.setRequestFactory(factory);
        return template;
    }}
