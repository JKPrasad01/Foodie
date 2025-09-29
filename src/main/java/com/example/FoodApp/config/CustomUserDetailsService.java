package com.example.FoodApp.config;

import com.example.FoodApp.Exception.UserNotFoundException;
import com.example.FoodApp.entity.User;
import com.example.FoodApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private static final Logger logger= LoggerFactory.getLogger(CustomUserDetailsService.class);
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.debug("Loading user by userName:{} ",username);
       User user=  userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
       logger.debug("User found : username : {} ,enabled :{} , role : {}",user.getUsername(),user.isEnabled(),user.getRole());
       return new CustomUser(user);
    }


    public UserDetails loadUserByUserEmail(String userEmail){
        logger.debug("Loading user Details by user email : {} ",userEmail);
        User user=userRepository.findByUserEmail(userEmail).orElseThrow(()->new UserNotFoundException("User not found by user email "+userEmail));
        logger.debug("User Details : userEmail :{} ",userEmail);
        return new CustomUser(user);
    }
}
