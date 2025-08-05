package com.example.FoodApp.service.Service;

import com.example.FoodApp.dto.OrderItemDTO;


public interface OrderItemService {

    OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO);
    OrderItemDTO getOrderItemById(Long orderItemId);
    OrderItemDTO updateOrderItem(Long orderItemId,OrderItemDTO orderItemDTO);
    String deleteOrderItem(Long orderItemId);
}
