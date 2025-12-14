package com.dkproject.microservices.shopping.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for Payment Requests
 * 
 * Represents a payment request with validation constraints.
 */
public class PaymentRequest {

    @NotNull(message = "Price cannot be null")
    @Min(value = 1, message = "Price must be at least 1")
    private Integer price;

    public PaymentRequest() {
    }

    public PaymentRequest(Integer price) {
        this.price = price;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "PaymentRequest{" +
                "price=" + price +
                '}';
    }
}
