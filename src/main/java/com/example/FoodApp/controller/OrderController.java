package com.example.FoodApp.controller;

import com.example.FoodApp.Enum.OrderStatus;
import com.example.FoodApp.dto.OrderDTO;
import com.example.FoodApp.dto.OrderHistory;
import com.example.FoodApp.service.Service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.createOrder(orderDTO));
    }

    @GetMapping("/get/{orderNumber}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable String orderNumber) {
        return ResponseEntity.ok(orderService.getOrderById(orderNumber));
    }

    @PutMapping("/update/{orderNumber}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable String orderNumber, @RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.updateOrder(orderNumber, orderDTO));
    }

    @DeleteMapping("/delete/{orderNumber}")
    public ResponseEntity<String> deleteOrder(@PathVariable String orderNumber) {
        return ResponseEntity.ok(orderService.deleteOrder(orderNumber));
    }

    @GetMapping("/by-customer/{userId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByCustomerId(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<OrderDTO>> getOrdersByStatus(@RequestParam OrderStatus orderStatus) {
        return ResponseEntity.ok(orderService.getOrdersByOrderStatus(orderStatus));
    }

    @GetMapping("/by-date")
    public ResponseEntity<List<OrderDTO>> getOrdersByDate(@RequestParam String date) {
        return ResponseEntity.ok(orderService.getOrdersByDate(LocalDateTime.parse(date)));
    }


    @GetMapping("/{userId}")
    public ResponseEntity<List<OrderHistory>> fetchHistoryByCustomerId(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.fetchHistory(userId));
    }
}
