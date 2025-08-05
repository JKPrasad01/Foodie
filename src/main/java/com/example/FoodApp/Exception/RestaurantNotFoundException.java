package com.example.FoodApp.Exception;

public class RestaurantNotFoundException extends RuntimeException {
    public RestaurantNotFoundException(Long message) {
        super("Restaurant Details not found :"+message);
    }
}
