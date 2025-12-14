package com.dkproject.microservices.shopping.dto;

/**
 * Payment Response DTO
 */
public class PaymentResponse {

    private String message;
    private Integer amount;
    private boolean success;
    private String transactionId;

    public PaymentResponse() {
    }

    public PaymentResponse(String message, Integer amount, boolean success) {
        this.message = message;
        this.amount = amount;
        this.success = success;
    }

    public PaymentResponse(String message, Integer amount, boolean success, String transactionId) {
        this.message = message;
        this.amount = amount;
        this.success = success;
        this.transactionId = transactionId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
