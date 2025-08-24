package com.example.FoodApp.service.Service;

import com.example.FoodApp.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);
    UserDTO logInUser(String userEmail, String password);
    UserDTO getUserById(Long userId);
    UserDTO updateUser(Long userId,UserDTO userDTO);
    List<UserDTO> findAll();
    String deleteUser(Long userId);
}
