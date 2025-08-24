package com.example.FoodApp.dto;

import lombok.Data;

@Data
public class ItemHistory {
    private String menuName;
    private String menuProfile;
    private Double price;
    private Long quantity;
}
