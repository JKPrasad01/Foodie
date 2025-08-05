package com.example.FoodApp.entity;

import com.example.FoodApp.Enum.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Orders {

    @Id
    private String orderNumber;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long restaurantId;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<OrderItem> orderItemList;

    @Column(nullable = false)
    private String deliveryAddress;

    @Column(nullable = false)
    private String contactNumber;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus=OrderStatus.PENDING;

    @CreationTimestamp
    private LocalDateTime orderDate;

    private LocalDateTime deliveredDate;

    @PrePersist
    public void generateOrderNumber(){
        if(this.orderNumber==null){
            this.orderNumber="OD"+generateRandomNumber();
        }
    }

    private String generateRandomNumber(){
        SecureRandom random=new SecureRandom();
        StringBuilder sb=new StringBuilder(8);
        for(int i=0;i<8;i++){
            sb.append(random.nextInt(8));
        }
        return sb.toString();
    }
}
