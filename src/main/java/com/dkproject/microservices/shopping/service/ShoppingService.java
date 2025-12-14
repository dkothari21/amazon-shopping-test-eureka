package com.dkproject.microservices.shopping.service;

import com.dkproject.microservices.shopping.dto.PaymentResponse;
import com.dkproject.microservices.shopping.exception.PaymentServiceException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Shopping Service - Business Logic Layer
 * 
 * Handles payment processing by communicating with the Payment Service.
 * Implements circuit breaker pattern for resilience.
 */
@Service
public class ShoppingService {

    private static final Logger logger = LoggerFactory.getLogger(ShoppingService.class);
    private static final String PAYMENT_SERVICE_NAME = "PAYMENT-SERVICE";

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Processes a payment by calling the Payment Service.
     * 
     * Circuit breaker is configured to:
     * - Open after 5 failures in a 10-second window
     * - Stay open for 10 seconds before attempting recovery
     * - Use fallback method when circuit is open
     * 
     * @param price the amount to process
     * @return PaymentResponse with payment details
     * @throws PaymentServiceException if payment processing fails
     */
    @CircuitBreaker(name = "paymentService", fallbackMethod = "paymentFallback")
    public PaymentResponse processPayment(int price) {
        logger.info("Processing payment for amount: {}", price);

        try {
            String url = "http://" + PAYMENT_SERVICE_NAME + "/payment-provider/paynow/" + price;
            logger.debug("Calling Payment Service at: {}", url);

            PaymentResponse paymentResponse = restTemplate.getForObject(url, PaymentResponse.class);
            logger.info("Payment successful: {}", paymentResponse);

            return paymentResponse;
        } catch (Exception e) {
            logger.error("Error processing payment: {}", e.getMessage());
            throw new PaymentServiceException("Failed to process payment", e);
        }
    }

    /**
     * Fallback method when Payment Service is unavailable.
     * This method is called when the circuit breaker is open.
     * 
     * @param price     the amount that was being processed
     * @param throwable the exception that triggered the fallback
     * @return PaymentResponse with fallback message
     */
    private PaymentResponse paymentFallback(int price, Throwable throwable) {
        logger.warn("Payment service fallback triggered for amount: {}. Reason: {}",
                price, throwable.getMessage());

        return new PaymentResponse(
                "Payment service is temporarily unavailable. Your payment of " + price +
                        " will be processed shortly. Please check back later.",
                price,
                false);
    }
}
