package com.example.FoodApp.dto;

import com.example.FoodApp.Enum.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderHistory {

    private String orderId;
    private String restaurantName;
    private String orderAddress;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;
    private OrderStatus orderStatus;
    private List<ItemHistory> itemHistories;



}
