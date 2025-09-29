package com.example.FoodApp.dto;

import com.example.FoodApp.Enum.Role;
import com.example.FoodApp.config.CustomUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    @Data
    private  class UserDetailsResponse{
        private String username;
        private String email;
        private Role role;
    }
    private String token;
}
