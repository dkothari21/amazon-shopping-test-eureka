package com.dkproject.microservices.shopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Shopping Service Application
 * 
 * This service demonstrates service-to-service communication in a microservices
 * architecture. It acts as a client that discovers and calls the Payment
 * Service
 * through the Eureka Discovery Server.
 * 
 * Features:
 * - Service discovery via Eureka
 * - Circuit breaker for resilience
 * - Load-balanced REST calls
 * - API documentation with Swagger
 * 
 * Default port: 9999
 * Swagger UI: http://localhost:9999/swagger-ui.html
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ShoppingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoppingServiceApplication.class, args);
    }

}
