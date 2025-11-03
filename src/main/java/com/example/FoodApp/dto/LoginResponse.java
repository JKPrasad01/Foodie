package com.example.FoodApp.dto;

import com.example.FoodApp.entity.Role;
import lombok.Data;
import java.util.Set;

@Data
public class LoginResponse {
    private Long userId;
    private String username;
    private String userProfile;
    private Set<String> roles;
}
