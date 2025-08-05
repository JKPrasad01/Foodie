package com.example.FoodApp.controller;

import com.example.FoodApp.dto.OrderItemDTO;
import com.example.FoodApp.service.Service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order-items")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    @PostMapping("/create")
    public ResponseEntity<OrderItemDTO> createOrderItem(@RequestBody OrderItemDTO orderItemDTO) {
        return ResponseEntity.ok(orderItemService.createOrderItem(orderItemDTO));
    }

    @GetMapping("/get/{orderItemId}")
    public ResponseEntity<OrderItemDTO> getOrderItemById(@PathVariable Long orderItemId) {
        return ResponseEntity.ok(orderItemService.getOrderItemById(orderItemId));
    }

    @PutMapping("/update/{orderItemId}")
    public ResponseEntity<OrderItemDTO> updateOrderItem(@PathVariable Long orderItemId,
                                                        @RequestBody OrderItemDTO orderItemDTO) {
        return ResponseEntity.ok(orderItemService.updateOrderItem(orderItemId, orderItemDTO));
    }

    @DeleteMapping("/delete/{orderItemId}")
    public ResponseEntity<String> deleteOrderItem(@PathVariable Long orderItemId) {
        return ResponseEntity.ok(orderItemService.deleteOrderItem(orderItemId));
    }
}
