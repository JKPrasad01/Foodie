package com.example.FoodApp.service.Service;

import com.example.FoodApp.dto.RoleDTO;

import java.util.Set;

public interface RoleService {
    RoleDTO createNewRole(RoleDTO roleDTO);
    RoleDTO findById(Long roleId);
    Set<RoleDTO> findAll();
    RoleDTO updateRole(Long roleId, RoleDTO roleDTO);
    String deleteRoleById(Long roleId);
}
