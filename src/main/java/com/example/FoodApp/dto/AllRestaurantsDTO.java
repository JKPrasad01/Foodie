package com.example.FoodApp.dto;

import lombok.Data;

@Data
public class AllRestaurantsDTO {
    private Long restaurantId;
    private String restaurantName;
    private String restaurantProfile;
    private String cuisineType;
    private String restaurantAddress;
    private Double rating;
    private Boolean openOrClosed;
}
