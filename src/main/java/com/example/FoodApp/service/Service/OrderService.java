package com.example.FoodApp.service.Service;

import com.example.FoodApp.Enum.OrderStatus;
import com.example.FoodApp.dto.OrderDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    OrderDTO createOrder(OrderDTO orderDTO);
    OrderDTO getOrderById(String orderNumber);
    OrderDTO updateOrder(String orderNumber,OrderDTO orderDTO);
    String deleteOrder(String orderNumber);
    List<OrderDTO> getOrdersByUserId(Long userId);
    List<OrderDTO> getOrdersByOrderStatus(OrderStatus orderStatus);
    List<OrderDTO> getOrdersByDate(LocalDateTime orderDate);

}
