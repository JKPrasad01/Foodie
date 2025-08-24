package com.example.FoodApp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;
    private String menuName;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String menuProfile;
    private Double rating;
    private String description;
    private Double price;
}
