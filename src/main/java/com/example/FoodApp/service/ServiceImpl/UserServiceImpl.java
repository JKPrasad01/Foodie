package com.example.FoodApp.service.ServiceImpl;

import com.example.FoodApp.Exception.UserNotFoundException;
import com.example.FoodApp.dto.UserDTO;
import com.example.FoodApp.entity.User;
import com.example.FoodApp.repository.UserRepository;
import com.example.FoodApp.service.Service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    public final ModelMapper modelMapper;

    @Transactional
    @Override
    public UserDTO createUser(UserDTO userDTO){
        System.out.println(userDTO);
        User user=modelMapper.map(userDTO,User.class);
        User saved= userRepository.save(user);
        return modelMapper.map(saved,UserDTO.class);
    }

    @Override
    public UserDTO logInUser(String userEmail, String password){
        User user = userRepository.findByEmailAndPassword(userEmail,password).orElseThrow(()->new UserNotFoundException(userEmail+" and "+password));
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
        User updated=userRepository.save(user);
        return modelMapper.map(updated,UserDTO.class);
    }

    @Override
    public String deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("No User existed to Delete"+ userId));
        userRepository.delete(user);
        return "Deleted SuccessFully";
    }
}
