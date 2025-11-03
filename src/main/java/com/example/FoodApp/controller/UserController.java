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
@CrossOrigin(
        origins = "http://localhost:5173",
        allowCredentials = "true" // allow cookies from frontend
)
public class UserController {

    private final UserService userService;

    // Get all users
    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> findAll() {
        List<UserDTO> list = userService.findAll();
        return ResponseEntity.ok(list);
    }

    // Get user by username (clean endpoint)
    @GetMapping("/by-username/{username}")
    public ResponseEntity<UserUpdateDTO> getUserByUsername(@PathVariable String username) {
        UserUpdateDTO response = userService.getUserByUsername(username);
        return ResponseEntity.ok(response);
    }

    // Get user by ID
    @GetMapping("/by-id/{userId}")
    public ResponseEntity<UserUpdateDTO> getUserById(@PathVariable Long userId) {
        UserUpdateDTO response = userService.getUserById(userId);
        return ResponseEntity.ok(response);
    }

    //  Update user
    @PutMapping("/update/{username}")
    public ResponseEntity<UserUpdateDTO> updateUser(
            @PathVariable String username,
            @RequestBody UserUpdateDTO user
    ) {
        UserUpdateDTO dto = userService.updateUser(username, user);
        return ResponseEntity.ok(dto);
    }

    // Delete user
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        String response = userService.deleteUser(userId);
        return ResponseEntity.ok(response);
    }
}
