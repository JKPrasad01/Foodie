package com.example.FoodApp.config;

import com.example.FoodApp.dto.*;
import com.example.FoodApp.service.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth/user")
@CrossOrigin(
        origins = "http://localhost:5173",
        allowCredentials = "true" // critical: allows cookies in CORS
)
@RequiredArgsConstructor
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;

    // REGISTER USER
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody SignupRequest signupRequest) {
        UserDTO userDTO = userService.registerUser(signupRequest);
        return ResponseEntity.ok(userDTO);
    }

    // LOGIN USER
    @PostMapping("/login-user")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            logger.info("Login attempt for user: {}", loginRequest.getUsername());

            // Authenticate
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            // Load user details
            final UserDetails userDetails = customUserDetailsService
                    .loadUserByUsername(loginRequest.getUsername());

            // Generate JWT
            final String jwtToken = jwtUtil.generateJwtToken(userDetails);

            //Fetch user DTO (so you can send back profile info)
            UserDTO userDTO = userService.logInUser(loginRequest.getUsername(), loginRequest.getPassword());

            //Set JWT cookie
            ResponseCookie cookie = ResponseCookie.from("jwt", jwtToken)
                    .httpOnly(true)
                    .secure(false) // keep false for local dev (HTTPS only = true in prod)
                    .path("/")
                    .maxAge(60 * 60) // 1 hour
                    .sameSite("Lax") // important for local dev with CORS
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(userDTO);

        } catch (Exception ex) {
            logger.error("Login failed for user {}: {}", loginRequest.getUsername(), ex.getMessage());
            return ResponseEntity.status(401)
                    .body(Map.of("error", "Invalid username or password"));
        }
    }

    //  LOGOUT USER (clears cookie)
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0) // delete cookie
                .sameSite("Lax")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(Map.of("message", "Logged out successfully"));
    }
}
