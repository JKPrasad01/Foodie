package com.example.FoodApp.dto;


import com.example.FoodApp.entity.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class UserUpdateDTO {
    private Long userId;
    private String username;
    private String userProfile;
    private String userEmail;
    private Long contactNumber;
    private String address;
    private String bio;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdateTime;
}
