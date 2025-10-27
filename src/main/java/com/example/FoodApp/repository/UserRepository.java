package com.example.FoodApp.repository;

import com.example.FoodApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query("SELECT u FROM User u WHERE u.userEmail = :email AND u.password = :password")
    Optional<User> findByEmailAndPassword(@Param("email") String email, @Param("password") String password);

    Optional<User> findByUsername(@Param("username")String username);

    Optional<User> findByUserEmail(@Param("email") String email);

    Boolean existsByUsername(String username);

    Boolean existsByUserEmail(String userEmail);
}
