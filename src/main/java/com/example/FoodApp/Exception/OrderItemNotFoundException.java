package com.example.FoodApp.Exception;

public class OrderItemNotFoundException extends RuntimeException {
    public OrderItemNotFoundException(Long orderItemId) {
        super("Order Item not found "+orderItemId);
    }
}
