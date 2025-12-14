package com.dkproject.microservices.shopping.exception;

/**
 * Custom exception for payment service errors
 */
public class PaymentServiceException extends RuntimeException {

    public PaymentServiceException(String message) {
        super(message);
    }

    public PaymentServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
