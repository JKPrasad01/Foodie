package com.example.FoodApp.controller;

import com.example.FoodApp.dto.UserDTO;
import com.example.FoodApp.dto.UserUpdateDTO;
import com.example.FoodApp.service.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> findAll(){
        List<UserDTO> list = userService.findAll();
        return ResponseEntity.ok(list);
    }


    @GetMapping("/fetch-user/{username}")
    public ResponseEntity<UserUpdateDTO> getUserById(@PathVariable String username){
        UserUpdateDTO response = userService.getUserByUsername(username);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-user/{userId}")
    public ResponseEntity<UserUpdateDTO> getUserById(@PathVariable Long userId){
        UserUpdateDTO response = userService.getUserById(userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{username}")
    public ResponseEntity<UserUpdateDTO> updateUser(@PathVariable String username, @RequestBody UserUpdateDTO user){
        UserUpdateDTO dto = userService.updateUser(username,user);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId){
        String response = userService.deleteUser(userId);
        return ResponseEntity.ok(response);
    }

}
