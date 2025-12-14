package com.dkproject.microservices.shopping.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Application Configuration
 * 
 * Configures beans required for the Shopping Service including
 * load-balanced RestTemplate for service-to-service communication.
 */
@Configuration
public class AppConfig {

    /**
     * Creates a load-balanced RestTemplate bean.
     * The @LoadBalanced annotation enables client-side load balancing
     * and service discovery through Eureka.
     * 
     * @return RestTemplate configured for load balancing
     */
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
