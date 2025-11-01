package com.example.FoodApp.service.ServiceImpl;

import com.example.FoodApp.Exception.RoleExistsAlreadyException;
import com.example.FoodApp.Exception.RoleNotFoundException;
import com.example.FoodApp.dto.RoleDTO;
import com.example.FoodApp.entity.Role;
import com.example.FoodApp.repository.RoleRepository;
import com.example.FoodApp.service.Service.RoleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public RoleDTO createNewRole(RoleDTO roleDTO){

        if(roleRepository.existsByRole(roleDTO.getRole())){
            throw new RoleExistsAlreadyException(roleDTO.getRole());
        }

        Role role=modelMapper.map(roleDTO,Role.class);
        Role saved =roleRepository.save(role);
        return modelMapper.map(saved,RoleDTO.class);
    }

    @Override
    public RoleDTO findById(Long roleId){
        Role role=roleRepository.findById(roleId).orElseThrow(()->new RoleNotFoundException(String.valueOf(roleId)));
        return modelMapper.map(role,RoleDTO.class);
    }

    public Set<RoleDTO> findAll(){
       List<Role> list = roleRepository.findAll();
       return list.stream().map(role -> modelMapper.map(role
       ,RoleDTO.class)).collect(Collectors.toSet());
    }

    public RoleDTO updateRole(Long roleId, RoleDTO roleDTO){
        Role role = roleRepository.findById(roleId).orElseThrow(()->new RoleNotFoundException(String.valueOf(roleId)));
        modelMapper.map(roleDTO,role);
        Role update = roleRepository.save(role);
        return modelMapper.map(update,RoleDTO.class);
    }

    public String deleteRoleById(Long roleId){
        Role role = roleRepository.findById(roleId).orElseThrow(()->new RoleNotFoundException(String.valueOf(roleId)));
        roleRepository.delete(role);
        return "Role Successfully Deleted";
    }
}
