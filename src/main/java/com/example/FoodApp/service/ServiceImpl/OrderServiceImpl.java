package com.example.FoodApp.service.ServiceImpl;

import com.example.FoodApp.Enum.OrderStatus;
import com.example.FoodApp.Exception.OrderNotFoundException;
import com.example.FoodApp.dto.OrderDTO;
import com.example.FoodApp.entity.Orders;
import com.example.FoodApp.repository.OrderRepository;
import com.example.FoodApp.service.Service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public OrderDTO createOrder(OrderDTO orderDTO){
        Orders order=modelMapper.map(orderDTO,Orders.class);
        Orders saved = orderRepository.save(order);
        return modelMapper.map(saved,OrderDTO.class);
    }

    @Override
    public OrderDTO getOrderById(String orderNumber){
        Orders orders=orderRepository.findById(orderNumber).orElseThrow(()->new OrderNotFoundException(orderNumber));
        return modelMapper.map(orders,OrderDTO.class);
    }

    @Transcational
    @Override
    public OrderDTO updateOrder(String orderNumber,OrderDTO orderDTO){
        Orders orders=orderRepository.findById(orderNumber).orElseThrow(()->new OrderNotFoundException(orderNumber));
        modelMapper.map(orderDTO,orders);
        Orders updated = orderRepository.save(orders);
        return modelMapper.map(updated,OrderDTO.class);
    }

    @Override
    public String deleteOrder(String orderNumber){
        Orders orders=orderRepository.findById(orderNumber).orElseThrow(()->new OrderNotFoundException(orderNumber));
        orderRepository.delete(orders);
        return "Order Deleted Successfully";
    }


    @Override
    public List<OrderDTO> getOrdersByUserId(Long customerId) {
        return orderRepository.findByUserId(customerId)
                .stream()
                .map(orders -> modelMapper.map(orders, OrderDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrdersByOrderStatus(OrderStatus status) {
        return orderRepository.findByOrderStatus(status)
                .stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrdersByDate(LocalDateTime orderDate) {
        return orderRepository.findByOrderDate(orderDate)
                .stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList());
    }
}
