package com.example.FoodApp.dto;

import lombok.Data;


@Data
public class OrderItemDTO {
    private Long orderItemId;
    private Long menuId;
    private Long restaurantId;
    private Double price;
    private Integer quantity;
}
