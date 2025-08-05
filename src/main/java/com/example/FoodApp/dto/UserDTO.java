package com.example.FoodApp.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long userId;
    private String userName;
    private String userProfile;
    private String userEmail;
    private String password;
    private Long contactNumber;
    private String address;
}
