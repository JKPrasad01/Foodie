package com.example.FoodApp.service.ServiceImpl;

import com.example.FoodApp.Exception.RestaurantNotFoundException;
import com.example.FoodApp.dto.RestaurantDTO;
import com.example.FoodApp.entity.Restaurant;
import com.example.FoodApp.repository.RestaurantRepository;
import com.example.FoodApp.service.Service.RestaurantService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public RestaurantDTO createRestaurant(RestaurantDTO restaurantDTO){
        Restaurant restaurant=modelMapper.map(restaurantDTO,Restaurant.class);
        Restaurant saved = restaurantRepository.save(restaurant);
        return modelMapper.map(saved,RestaurantDTO.class);
    }

    @Override
    public RestaurantDTO getRestaurantById(Long restaurantId){
       Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(()->new RestaurantNotFoundException(restaurantId));
        return modelMapper.map(restaurant,RestaurantDTO.class);
    }

    @Override
    public List<RestaurantDTO> getAllRestaurants(){
        List<Restaurant> list=restaurantRepository.findAll();
        return list.stream().map(restaurant -> modelMapper.map(restaurant,RestaurantDTO.class)).toList();
    }

    @Transactional
    @Override
    public RestaurantDTO updateRestaurant(Long restaurantId,RestaurantDTO restaurantDTO){
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(()->new RestaurantNotFoundException(restaurantId));
        modelMapper.map(restaurantDTO,restaurant);
        Restaurant update=restaurantRepository.save(restaurant);
        return modelMapper.map(update,RestaurantDTO.class);
    }

    @Override
    public String deleteRestaurantId(Long restaurantId){
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(()->new RestaurantNotFoundException(restaurantId));
            restaurantRepository.delete(restaurant);
        return "Restaurant Details successfully Deleted";
    }
}
