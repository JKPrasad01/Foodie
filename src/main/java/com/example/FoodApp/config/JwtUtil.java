package com.example.FoodApp.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long expiration;



    // create token

    public String generateJwtToken(String username){
    Map<String , Object> claims=new HashMap<>();

    return createJwtToken(claims,username);
    }

    private String createJwtToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+expiration))
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();
    }


    // 2. read the token

    public String getUsernameFromToken(String token){
        return  extractClaim(token, Claims::getSubject);
    }

    public Date getExpiryDate(String token){
        return extractClaim(token,Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims,T> claimResolver) {
        final Claims claims=extractClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }



    // validation

    private Boolean validateToken(String token){
        return getExpiryDate(token).before(new Date());
    }

    private Boolean validation(String token,String username){
        final String extractedUsername =  getUsernameFromToken(token);
        return (extractedUsername.equals(username) && !validateToken(token));
    }
}
