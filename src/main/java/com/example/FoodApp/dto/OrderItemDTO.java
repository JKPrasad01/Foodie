package com.example.FoodApp.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
public class OrderItemDTO {
    private Long orderItemId;
    @NotNull(message = "menuId is should be null")
    private Long menuId;

    @NotNull(message = "specify required quantity")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Long quantity;
}
