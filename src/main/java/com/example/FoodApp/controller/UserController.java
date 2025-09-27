package com.example.FoodApp.controller;

//import com.example.FoodApp.config.JwtTokenProvider;
import com.example.FoodApp.config.JwtUtil;
import com.example.FoodApp.dto.UserDTO;
import com.example.FoodApp.service.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthenticationManager auth;
    private final JwtUtil jwtUtil;
//    private final JwtTokenProvider jwtTokenProvider;



    @PostMapping("/sign-up")
    public ResponseEntity<UserDTO> createUser(@Valid  @RequestBody UserDTO userDTO){
         UserDTO dto =userService.createUser(userDTO);
         return ResponseEntity.ok(dto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> findAll(){
        List<UserDTO> list = userService.findAll();
        return ResponseEntity.ok(list);
    }

//    @PostMapping("/log-in")
//    public ResponseEntity<LoginResponse> logIn(@RequestBody LoginRequest request){
//        if (request.getUserEmail() == null || request.getPassword() == null) {
//            throw new IllegalArgumentException("Email and password are required");
//        }
//        UserDTO userDTO=userService.logInUser(request.getUserEmail(),request.getPassword());
//        System.out.println(userDTO.toString());
//
//        String token =  jwtTokenProvider.createToken(request.getUserEmail());
//
//        System.out.println("token "+token);
//        return ResponseEntity.ok(new LoginResponse(userDTO,token));
//    }

//
//    public ResponseEntity<UserDTO> logInUser(@RequestBody UserDTO loginRequest) {
//        // Only use email & password from loginRequest
//        return ResponseEntity.ok(
//                userService.logInUser(loginRequest.getUserEmail(), loginRequest.getPassword())
//        );
//    }

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
