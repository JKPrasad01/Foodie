package com.example.FoodApp.Exception;

public class RoleExistsAlreadyException extends RuntimeException {
    public RoleExistsAlreadyException(String message) {
        super(message);
    }
}
