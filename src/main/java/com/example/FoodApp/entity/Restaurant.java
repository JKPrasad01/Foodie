package com.example.FoodApp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantId;

    @Column(nullable = false)
    private String restaurantName;

    private String restaurantProfile;

    @Column(nullable = false)
    private String cuisineType;

    @Column(nullable = false)
    private String restaurantAddress;

    private Double rating;

    private Boolean openOrClosed;

    @OneToMany(cascade = CascadeType.ALL , orphanRemoval = true ,fetch = FetchType.LAZY)
    private List<Menu> menuList;
}
