package com.example.FoodApp.config;

import com.example.FoodApp.dto.LoginRequest;
import com.example.FoodApp.dto.LoginResponse;
import com.example.FoodApp.dto.SignupRequest;
import com.example.FoodApp.dto.UserDTO;
import com.example.FoodApp.service.Service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/auth/user")
@CrossOrigin(
        origins = "http://localhost:5173",  // your frontend
        allowCredentials = "true",          // allow cookies
        maxAge = 3600
)
@RequiredArgsConstructor
public class AuthController {
    private static final Logger logger= LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody SignupRequest signupRequest){
        UserDTO userDTO = userService.registerUser(signupRequest);
        return ResponseEntity.ok(userDTO);
    }

//    @PostMapping("/login-user")
//    public ResponseEntity<?> loginUser( @RequestBody LoginRequest loginRequest){
//        try {
//            logger.info("login details : username : {} ",loginRequest.getUsername());
//
//            final UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getUsername());
//            final String jwtToken = jwtUtil.generateJwtToken(userDetails);
//            return ResponseEntity.ok(new LoginResponse( jwtToken));
//        } catch (Exception ex) {
//            logger.warn("Invalid username : {}",loginRequest.getUsername() );
//            return ResponseEntity
//                    .status(401)
//                    .body(Map.of("error", "Invalid username or password"));
//        }
//    }

    @PostMapping("/login-user")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            // 1. Authenticate
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            // 2. Load user details
            final UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getUsername());

            // 3. Generate JWT
            final String jwtToken = jwtUtil.generateJwtToken(userDetails);

            // 4. Get UserDTO
            UserDTO response = userService.logInUser(loginRequest.getUsername(), loginRequest.getPassword());

            // 5. Set cookie (for dev: secure=false)
            ResponseCookie cookie = ResponseCookie.from("jwt", jwtToken)
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(60*60)
                    .domain("localhost")
                    .secure(true)
                    .build();

            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(response);

        } catch (Exception ex) {
            ex.printStackTrace(); // shows exact reason
            return ResponseEntity.status(401).body(Map.of("error", "Invalid username or password"));
        }
    }

}
