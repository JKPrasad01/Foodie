package com.example.FoodApp.config;


import com.example.FoodApp.dto.LoginRequest;
import com.example.FoodApp.dto.LoginResponse;

import com.example.FoodApp.dto.SignupRequest;
import com.example.FoodApp.dto.UserDTO;
import com.example.FoodApp.service.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/user")
@CrossOrigin(origins = "*",maxAge = 3600)
@RequiredArgsConstructor
public class AuthController {
    private static final Logger logger= LoggerFactory.getLogger(AuthController.class);


    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;


    @PostMapping("register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody SignupRequest signupRequest){
        userService.
    }










    @PostMapping("login-user")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
        final UserDetails userDetails=customUserDetailsService.loadUserByUsername(loginRequest.getUsername());
        final String jwtToken = jwtUtil.generateJwtToken(userDetails);
        return ResponseEntity.ok(new LoginResponse(userDetails,jwtToken ));
    }
}
