package com.example.FoodApp.dto;

import com.example.FoodApp.Enum.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private String orderNumber;
    private Long userId;
    private Long restaurantId;
    private List<OrderItemDTO> orderItemList;
    private String deliveryAddress;
    private String contactNumber;
    private OrderStatus orderStatus;
    private LocalDateTime orderDate;
    private LocalDateTime deliveredDate;
}
