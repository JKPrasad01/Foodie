package com.example.FoodApp.entity;

import com.example.FoodApp.Enum.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false,unique = true,length = 20)
    private String userName;

    @Lob
    @Column(name = "user_profile", columnDefinition = "LONGTEXT")
    private String userProfile;

    @Column(unique = true, nullable = false,length = 50)
    private String userEmail;

    @Column(nullable = false)
    private String password;

    private Long contactNumber;

    private String address;

    private String bio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role=Role.USER;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdateTime;

    // Account Status Fields

    @Column(nullable = false)
    private boolean enabled=true;

    @Column(nullable = false)
    private boolean accountNonExpired=true;

    @Column(nullable = false)
    private boolean accountNonLocked=true;

    @Column(nullable = false)
    private boolean credentialsNonExpired=true;

    public boolean isActive(){
        return enabled && accountNonExpired && accountNonLocked && credentialsNonExpired;
    }
}
