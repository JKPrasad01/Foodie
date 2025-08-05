package com.example.FoodApp.Exception;

public class MenuNotFoundException extends RuntimeException {
    public MenuNotFoundException(Long message) {
        super("Menu details not Found :"+message);
    }
}
