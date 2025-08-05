package com.example.FoodApp.controller;

import com.example.FoodApp.dto.RestaurantDTO;
import com.example.FoodApp.service.Service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;

    @PostMapping("/create")
    public ResponseEntity<RestaurantDTO> createRestaurant(@RequestBody RestaurantDTO restaurantDTO){
        return ResponseEntity.ok(restaurantService.createRestaurant(restaurantDTO));
    }

    @GetMapping("-get/{restaurantId}")
    public ResponseEntity<RestaurantDTO> getRestaurantById(@PathVariable Long restaurantId){
        return ResponseEntity.ok(restaurantService.getRestaurantById(restaurantId));
    }

    @GetMapping("-all")
    public ResponseEntity<List<RestaurantDTO>> getAll(){
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }
    @PutMapping("-update/{restaurantId}")
    public ResponseEntity<RestaurantDTO> updateRestaurant(@PathVariable Long restaurantId , @RequestBody RestaurantDTO restaurantDTO){
        return ResponseEntity.ok(restaurantService.updateRestaurant(restaurantId,restaurantDTO));
    }

    @DeleteMapping("-delete/{restaurantId}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable Long restaurantId ){
        return ResponseEntity.ok(restaurantService.deleteRestaurantId(restaurantId));
    }

}
