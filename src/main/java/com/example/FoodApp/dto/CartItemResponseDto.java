package com.example.FoodApp.dto;

import lombok.Data;

@Data
public class CartItemResponseDto {
    private Long cartItemId;
    private String menuName;
    private Long stock;
    private Double price;
}
