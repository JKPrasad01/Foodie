package com.example.FoodApp.config;

import com.example.FoodApp.entity.User;
import com.example.FoodApp.repository.UserRepository;
import com.example.FoodApp.service.Service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    private final UserRepository userRepository;
    private String secretKey = "uW8I3J5l7e+XoP2Z7a+HvLFk5vW2Y3q2N4OexY0l2Fs="; // Make sure to store this securely, e.g., in environment variables
    private long validityInMilliseconds = 3600000; // 1 hour

    public JwtTokenProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public String createToken(String username) {
        Claims claims = Jwts.claims().setSubject(username);
//        claims.put("roles", roles);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        String email = extractUsername(token); // Get 'sub' claim from token
        User user = userRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getUserEmail())
                .password(user.getPassword()) // not used in token auth
//                .authorities(user.getAuthorities()) // or Collections.emptyList() if you don’t use roles
                .build();

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }



    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
}
