package com.dkproject.microservices.shopping.service;

import com.dkproject.microservices.shopping.dto.PaymentResponse;
import com.dkproject.microservices.shopping.exception.PaymentServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShoppingServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ShoppingService shoppingService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void processPayment_WhenSuccessful_ShouldReturnResponse() {
        // Arrange
        int price = 100;
        String successMessage = "payment with 100 is successfull";
        when(restTemplate.getForObject(anyString(), eq(String.class)))
                .thenReturn(successMessage);

        // Act
        PaymentResponse response = shoppingService.processPayment(price);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getAmount()).isEqualTo(price);
        assertThat(response.getMessage()).isEqualTo(successMessage);
    }

    @Test
    void processPayment_WhenServiceFails_ShouldThrowException() {
        // Arrange
        int price = 100;
        when(restTemplate.getForObject(anyString(), eq(String.class)))
                .thenThrow(new RestClientException("Connection refused"));

        // Act & Assert
        assertThatThrownBy(() -> shoppingService.processPayment(price))
                .isInstanceOf(PaymentServiceException.class)
                .hasMessageContaining("Failed to process payment");
    }
}
