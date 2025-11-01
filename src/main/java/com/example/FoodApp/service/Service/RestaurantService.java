package com.example.FoodApp.service.Service;

import com.example.FoodApp.dto.AllRestaurantsDTO;
import com.example.FoodApp.dto.MenuDTO;
import com.example.FoodApp.dto.RestaurantDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RestaurantService {
    RestaurantDTO createRestaurant(RestaurantDTO restaurantDTO);

    RestaurantDTO createRestaurant(RestaurantDTO dto, MultipartFile restaurantImage, MultipartFile[] menuImages) throws IOException;
    List<AllRestaurantsDTO> allRestaurants();

    RestaurantDTO createRestaurantWithImages(RestaurantDTO restaurantDTO, MultipartFile restaurantImage, List<MultipartFile> menuImages) throws IOException;
    List<RestaurantDTO> createBulkRestaurant(List<RestaurantDTO> restaurantDTO);
    RestaurantDTO getRestaurantById(Long restaurantId);
    List<RestaurantDTO> getAllRestaurants();
    RestaurantDTO updateRestaurant(Long restaurantId, RestaurantDTO restaurantDTO);
    String deleteRestaurantId(Long restaurantId);
    List<MenuDTO> getAllMenu(Long restaurantId);
//    void importRestaurantsFromExcel(MultipartFile file) throws IOException;
    void saveRestaurantsFromDTOList(List<RestaurantDTO> restaurants);
}
