package com.example.FoodApp.controller;

import com.example.FoodApp.dto.RoleDTO;
import com.example.FoodApp.service.Service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleDTO> createNewRole(@RequestBody RoleDTO roleDTO){
        return ResponseEntity.ok(roleService.createNewRole(roleDTO));
    }

    @GetMapping("/role/{roleId}")
    public ResponseEntity<RoleDTO> findById(@PathVariable Long roleId){
        return ResponseEntity.ok(roleService.findById(roleId));
    }

    @GetMapping("/all")
    public ResponseEntity<Set<RoleDTO>> findAll(){
        return ResponseEntity.ok(roleService.findAll());
    }

    @PutMapping("/update-role/{roleId}")
    public ResponseEntity<RoleDTO> updateRole(@PathVariable Long roleId,@RequestBody RoleDTO roleDTO){
        return ResponseEntity.ok(roleService.updateRole(roleId,roleDTO));
    }

    @PutMapping("/delete-role/{roleId}")
    public ResponseEntity<String> updateRole(@PathVariable Long roleId){
        return ResponseEntity.ok(roleService.deleteRoleById(roleId));
    }
}
