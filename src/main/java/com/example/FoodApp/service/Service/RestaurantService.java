package com.example.FoodApp.service.Service;

import com.example.FoodApp.dto.RestaurantDTO;

import java.util.List;

public interface RestaurantService {

    RestaurantDTO createRestaurant(RestaurantDTO restaurantDTO);
    RestaurantDTO getRestaurantById(Long restaurantId);
    List<RestaurantDTO> getAllRestaurants();
    RestaurantDTO updateRestaurant(Long restaurantId,RestaurantDTO restaurantDTO);
    String deleteRestaurantId(Long restaurantId);
}
