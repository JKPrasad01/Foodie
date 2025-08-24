package com.example.FoodApp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private Long userId;
    private String userName;
    private String userProfile;
    private String userEmail;
    private String password;
    private Long contactNumber;
    private String address;
    private String bio;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdateTime;
}
