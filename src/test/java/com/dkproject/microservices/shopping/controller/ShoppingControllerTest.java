package com.dkproject.microservices.shopping.controller;

import com.dkproject.microservices.shopping.dto.PaymentResponse;
import com.dkproject.microservices.shopping.exception.GlobalExceptionHandler;
import com.dkproject.microservices.shopping.service.ShoppingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShoppingController.class)
@Import(GlobalExceptionHandler.class)
class ShoppingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoppingService shoppingService;

    @Test
    void processPayment_ValidPrice_ShouldReturnOk() throws Exception {
        // Arrange
        int price = 100;
        PaymentResponse response = new PaymentResponse("Success", price, true);
        when(shoppingService.processPayment(price)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(get("/api/shopping/payment/{price}", price))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(price))
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void processPayment_InvalidPrice_ShouldReturnBadRequest() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/shopping/payment/{price}", 0))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Error"));
    }
}
