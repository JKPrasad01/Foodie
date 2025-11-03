package com.example.FoodApp.service.Service;

import com.example.FoodApp.dto.LoginResponse;
import com.example.FoodApp.dto.SignupRequest;
import com.example.FoodApp.dto.UserDTO;
import com.example.FoodApp.dto.UserUpdateDTO;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);
    UserDTO registerUser(SignupRequest signupRequest);
    LoginResponse logInUser(String username, String password);
    UserUpdateDTO getUserById(Long userId);
    UserUpdateDTO updateUser(String username,UserUpdateDTO userUpdateDTO);
    List<UserDTO> findAll();
    UserUpdateDTO getUserByUsername(String username);
    String deleteUser(Long userId);
}
