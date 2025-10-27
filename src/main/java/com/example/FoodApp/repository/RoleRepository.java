package com.example.FoodApp.repository;

import com.example.FoodApp.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {


    Optional<Role> findByRole(String role);

    Boolean existsByRole(String role);
}
