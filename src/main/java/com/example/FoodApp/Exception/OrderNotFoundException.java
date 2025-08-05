package com.example.FoodApp.Exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super("Order not found "+ message);
    }
}
