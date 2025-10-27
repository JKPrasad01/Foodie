package com.example.FoodApp.service.ServiceImpl;

import com.example.FoodApp.Enum.Role;
import com.example.FoodApp.Exception.UserNotFoundException;
import com.example.FoodApp.dto.SignupRequest;
import com.example.FoodApp.dto.UserDTO;
import com.example.FoodApp.entity.User;
import com.example.FoodApp.repository.UserRepository;
import com.example.FoodApp.service.Service.UserService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger=  LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    public UserDTO createUser(UserDTO userDTO){
        System.out.println(userDTO);
        User user=modelMapper.map(userDTO,User.class);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User saved= userRepository.save(user);
        return modelMapper.map(saved,UserDTO.class);
    }

    @Override
    public UserDTO logInUser(String username, String password){

        User user = userRepository.findByUsername(username).orElseThrow(()->new UserNotFoundException(username+" and "+password));

        System.out.println(password);
        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new BadCredentialsException("Invalid Password");
        }

        return modelMapper.map(user,UserDTO.class);
    }

    @Override
    public UserDTO getUserById(Long userId){
            User user = userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("User not found "+ userId));
            return modelMapper.map(user,UserDTO.class);
    }

    public List<UserDTO> findAll(){
       List<User> list = userRepository.findAll();
       return list.stream().map(user -> modelMapper.map(user, UserDTO.class)).toList();
    }

    @Override
    public UserDTO updateUser(Long userId,UserDTO userDTO){
        User user = userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("User not found "+ userId));


        modelMapper.map(userDTO,user);
        user.setLastUpdateTime(LocalDateTime.now());
        if(userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()){
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        User updated=userRepository.save(user);
        return modelMapper.map(updated,UserDTO.class);
    }

    @Override
    public String deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("No User existed to Delete"+ userId));
        userRepository.delete(user);
        return "Deleted SuccessFully";
    }



    public UserDTO registerUser(SignupRequest signupRequest){
        logger.info("Starting registration process for username: '{}' :" , signupRequest.getUsername());

        try{
            // validate user name
            if(userRepository.existsByUsername(signupRequest.getUsername())){
                logger.warn("Registration failed : UserName '{}' is Already taken",signupRequest.getUsername());
            }

            //validate user email
            if (userRepository.existsByUserEmail(signupRequest.getEmail())){
                logger.warn("Registration failed : Email '{}' is Already existed",signupRequest.getEmail());
            }

            // create user
            User user=mapToUser(signupRequest);
            User saved = userRepository.save(user);

            logger.info("User registration successfully : ID:{},username:{} ,email : {}",saved.getUserId(),saved.getUsername(),saved.getUserEmail());
            return modelMapper.map(saved,UserDTO.class);
        }
        catch (Exception e){
            logger.error("Error during registration ",e);
            throw e;
        }
    }


    private User mapToUser(SignupRequest signupRequest){
        User user=new User();
        user.setUsername(signupRequest.getUsername());
        user.setUserEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
//        user.setContactNumber(signupRequest.getContactNumber());
        user.setRole(Role.USER);

        return user;
    }

    @Transactional(readOnly = true)
    public boolean isUsernameAvailable(String userName){
        return userRepository.existsByUsername(userName);
    }
}
