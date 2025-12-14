package com.dkproject.microservices.shopping.controller;

import com.dkproject.microservices.shopping.dto.PaymentResponse;
import com.dkproject.microservices.shopping.service.ShoppingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Shopping Controller - REST API Endpoints
 * 
 * Provides endpoints for shopping operations including payment processing.
 * This controller demonstrates service-to-service communication via Eureka.
 */
@RestController
@RequestMapping("/api/shopping")
@Validated
@Tag(name = "Shopping", description = "Shopping service API for payment processing")
public class ShoppingController {

    private static final Logger logger = LoggerFactory.getLogger(ShoppingController.class);

    @Autowired
    private ShoppingService shoppingService;

    /**
     * Process a payment through the Payment Service.
     * 
     * This endpoint demonstrates:
     * - Service discovery via Eureka
     * - Load-balanced REST calls
     * - Circuit breaker pattern
     * - Input validation
     * 
     * @param price the amount to process (must be >= 1)
     * @return PaymentResponse with payment details
     */
    @GetMapping("/payment/{price}")
    @Operation(summary = "Process a payment", description = "Processes a payment by calling the Payment Service through Eureka service discovery")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment processed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid price value"),
            @ApiResponse(responseCode = "503", description = "Payment service unavailable")
    })
    public ResponseEntity<PaymentResponse> processPayment(
            @Parameter(description = "Amount to process (minimum 1)", required = true) @PathVariable("price") @Min(value = 1, message = "Price must be at least 1") int price) {

        logger.info("Received payment request for amount: {}", price);
        PaymentResponse response = shoppingService.processPayment(price);
        return ResponseEntity.ok(response);
    }

    /**
     * Health check endpoint for the shopping service.
     * 
     * @return Simple health status message
     */
    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Simple health check endpoint to verify service is running")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Shopping Service is running");
    }
}
