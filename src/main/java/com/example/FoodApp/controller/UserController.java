package com.example.FoodApp.controller;

import com.example.FoodApp.dto.UserDTO;
import com.example.FoodApp.service.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO){
         UserDTO dto =userService.createUser(userDTO);
         return ResponseEntity.ok(dto);
    }

    @GetMapping("/log-in?{userEmail}&{password}")
    public ResponseEntity<UserDTO> getUser(@RequestParam String userEmail, @RequestParam String password){
        return ResponseEntity.ok(userService.logInUser(userEmail,password));
    }

    @GetMapping("/get-user?{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId){
        UserDTO userDTO = userService.getUserById(userId);
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/update?{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId,@RequestBody UserDTO userDTO){
        UserDTO dto = userService.updateUser(userId,userDTO);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/user?{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId){
        String response = userService.deleteUser(userId);
        return ResponseEntity.ok(response);
    }

}
