package com.example.FoodApp.service.ServiceImpl;

import com.example.FoodApp.Exception.OrderItemNotFoundException;
import com.example.FoodApp.dto.OrderItemDTO;
import com.example.FoodApp.entity.OrderItem;
import com.example.FoodApp.repository.OrderItemRepository;
import com.example.FoodApp.service.Service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO){
        OrderItem orderItem=modelMapper.map(orderItemDTO,OrderItem.class);
        OrderItem saved = orderItemRepository.save(orderItem);
        return modelMapper.map(saved,OrderItemDTO.class);
    }
    @Override
    public OrderItemDTO getOrderItemById(Long orderItemId){
        OrderItem orderItem = orderItemRepository.findById(orderItemId).orElseThrow(()->new OrderItemNotFoundException(orderItemId));
        return modelMapper.map(orderItem,OrderItemDTO.class);

    }
    @Override
    public OrderItemDTO updateOrderItem(Long orderItemId,OrderItemDTO orderItemDTO){
        OrderItem orderItem = orderItemRepository.findById(orderItemId).orElseThrow(()->new OrderItemNotFoundException(orderItemId));
        modelMapper.map(orderItemDTO,orderItem);
        OrderItem updated = orderItemRepository.save(orderItem);
        return modelMapper.map(updated,OrderItemDTO.class);
    }
    @Override
    public String deleteOrderItem(Long orderItemId){
        OrderItem orderItem = orderItemRepository.findById(orderItemId).orElseThrow(()->new OrderItemNotFoundException(orderItemId));
        orderItemRepository.delete(orderItem);
        return "Order-Item deleted Successfully";
    }
}
