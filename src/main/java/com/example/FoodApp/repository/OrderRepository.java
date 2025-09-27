package com.example.FoodApp.repository;

import com.example.FoodApp.Enum.OrderStatus;
import com.example.FoodApp.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,String> {

    List<Orders> findByUserId(Long customerId);

    List<Orders> findByOrderStatus(OrderStatus orderStatus);

    List<Orders> findByOrderDate(LocalDateTime orderDate);
}
