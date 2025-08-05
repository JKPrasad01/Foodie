package com.example.FoodApp.dto;

import lombok.Data;
import java.util.List;

@Data
public class RestaurantDTO {
    private Long restaurantId;
    private String restaurantName;
    private String restaurantProfile;
    private String cuisineType;
    private String restaurantAddress;
    private Double rating;
    private Boolean openOrClosed;
    private List<MenuDTO> menuList;
}
