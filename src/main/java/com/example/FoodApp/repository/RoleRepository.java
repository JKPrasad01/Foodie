package com.example.FoodApp.repository;

import com.example.FoodApp.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {


    Boolean existsByRole(String role);
}
