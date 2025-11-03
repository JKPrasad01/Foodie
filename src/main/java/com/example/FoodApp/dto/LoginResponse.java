package com.example.FoodApp.dto;

import com.example.FoodApp.Enum.Role;
import com.example.FoodApp.config.CustomUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.Set;

@Data
@Builder
public class LoginResponse {
    private Long userId;
    private String username;
    private String userProfile;
    private Set<String> roles;
}
