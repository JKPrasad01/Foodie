package com.example.FoodApp.dto;



import com.example.FoodApp.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
        private Long userId;
        private String username;
        private Set<Role> role;
}
