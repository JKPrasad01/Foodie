package com.example.FoodApp.dto;

import lombok.Data;

import java.util.List;

@Data
public class MenuDTO {
    private Long menuId;
    private String menuName;
    private String menuProfile;
    private Double rating;
    private String description;
    private Double price;
}