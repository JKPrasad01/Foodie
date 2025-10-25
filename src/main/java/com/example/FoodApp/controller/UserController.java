package com.example.FoodApp.controller;

import com.example.FoodApp.dto.UserDTO;
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

    @GetMapping("/get-user/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId){
        UserDTO userDTO = userService.getUserById(userId);
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId,@RequestBody UserDTO userDTO){
        UserDTO dto = userService.updateUser(userId,userDTO);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId){
        String response = userService.deleteUser(userId);
        return ResponseEntity.ok(response);
    }

}
