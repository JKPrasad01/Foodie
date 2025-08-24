package com.example.FoodApp.dto;

import com.example.FoodApp.Enum.OrderStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private String orderNumber;

    @NotNull(message = "User should be Logged In")
    private Long userId;

    @NotNull(message = "Restaurant Id required")
    private Long restaurantId;

    @NotNull(message = "order-items are required")
    @Valid
    private List<OrderItemDTO> orderItemList;

    @NotBlank(message = "Delivery Address is needed to Delivery the food")
    private String deliveryAddress;

    @NotBlank(message = "Contact Details required to contact")
    private String contactNumber;

    private OrderStatus orderStatus;
    private LocalDateTime orderDate;
    private LocalDateTime deliveredDate;

    @NotBlank(message = "payment status should not be empty")
    private String paymentStatus;
}
