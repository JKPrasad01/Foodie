package com.example.FoodApp.service.ServiceImpl;

import com.example.FoodApp.Enum.OrderStatus;
import com.example.FoodApp.Exception.OrderNotFoundException;
import com.example.FoodApp.dto.ItemHistory;
import com.example.FoodApp.dto.OrderDTO;
import com.example.FoodApp.dto.OrderHistory;
import com.example.FoodApp.entity.Menu;
import com.example.FoodApp.entity.Orders;
import com.example.FoodApp.entity.Restaurant;
import com.example.FoodApp.repository.MenuRepository;
import com.example.FoodApp.repository.OrderRepository;
import com.example.FoodApp.repository.RestaurantRepository;
import com.example.FoodApp.service.Service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;

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


    public List<OrderHistory> fetchHistory(Long userId){

        List<Orders> list =orderRepository.findByUserId(userId);

        List<OrderHistory> oh=new ArrayList<>();
        list.stream().forEach(orders -> {

            Restaurant restaurant=restaurantRepository.findById(orders.getRestaurantId()).get();
            OrderHistory orderHistory=new OrderHistory();


            orderHistory.setOrderId(orders.getOrderNumber());
            orderHistory.setRestaurantName(restaurant.getRestaurantName());
            orderHistory.setOrderAddress(orders.getDeliveryAddress());
            orderHistory.setOrderDate(orders.getOrderDate());
            orderHistory.setDeliveryDate(orderHistory.getOrderDate().plusHours(2));
            orderHistory.setOrderStatus(orders.getOrderStatus());


            List<ItemHistory> historyList=new ArrayList<>();
            orders.getOrderItemList().stream().forEach(orderItem -> {

                Menu menu=menuRepository.findById(orderItem.getMenuId()).get();
                ItemHistory itemHistory=new ItemHistory();

                itemHistory.setMenuName(menu.getMenuName());
                itemHistory.setMenuProfile(menu.getMenuProfile());
                itemHistory.setQuantity(orderItem.getQuantity());
                itemHistory.setPrice(menu.getPrice());

                historyList.add(itemHistory);
            });
            orderHistory.setItemHistories(historyList);
            oh.add(orderHistory);
        });

        return oh;
    }
}
