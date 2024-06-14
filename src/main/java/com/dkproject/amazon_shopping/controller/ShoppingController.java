package com.dkproject.amazon_shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ShoppingController {

    @Autowired
    private RestTemplate template;

    @GetMapping("/amazon-payment/{price}")
    public String invokePaymentService(@PathVariable("price") int price){
        String url = "http://PAYMENT-SERVICE/payment-provider/paynow/"+price;
        System.out.println(url);
        return template.getForObject(url, String.class);
    }
    
}
