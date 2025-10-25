
package com.example.FoodApp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
       httpSecurity.

           //1. disable csrf protection
           csrf(AbstractHttpConfigurer::disable)

           //2. enable cors configuration
           .cors(cors->cors.configurationSource(corsConfigurationSource()))

           //3.configure URL authorization rules
           .authorizeHttpRequests(auth->auth
                   .requestMatchers(
                           "/auth/user/**",
                           "/swagger-ui.html",
                           "/swagger-ui/**",
                           "/v3/api-docs/**").permitAll()
                   .anyRequest().authenticated())

           //4.session management to be stateless (common for JWT)
           .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

           //5.set custom authentication provider
           .authenticationProvider(authenticationProvider())

           //6. add custom JWT filter before the default Username and passwordAuthenticationFilter
           .addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);

       return httpSecurity.build();
   }


    @Bean
    public AuthenticationProvider authenticationProvider(){
       DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
       authenticationProvider.setPasswordEncoder(passwordEncoder());
       authenticationProvider.setUserDetailsService(customUserDetailsService);
       return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder();
   }

   @Bean
   public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
       return configuration.getAuthenticationManager();
   }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}

