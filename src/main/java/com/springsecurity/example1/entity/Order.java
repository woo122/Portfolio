package com.springsecurity.example1.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();
    
    @Column(nullable = false)
    private LocalDateTime orderDate;
    
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    
    private String deliveryAddress;
    private String recipientName;
    private String recipientPhone;
    
    @Builder
    public Order(User user, List<OrderItem> orderItems, String deliveryAddress, 
                String recipientName, String recipientPhone) {
        this.user = user;
        this.orderDate = LocalDateTime.now();
        this.status = OrderStatus.ORDER;
        this.deliveryAddress = deliveryAddress;
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
        
        for (OrderItem orderItem : orderItems) {
            addOrderItem(orderItem);
        }
    }
    
    // 주문 상품 추가
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    
    // 주문 취소
    public void cancel() {
        if (this.status == OrderStatus.DELIVERY || this.status == OrderStatus.COMPLETE) {
            throw new IllegalStateException("이미 배송 중이거나 완료된 주문은 취소가 불가능합니다.");
        }
        
        this.status = OrderStatus.CANCEL;
        
        // 재고 복구
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }
    
    // 주문 총액 계산
    public int getTotalPrice() {
        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }
    
    // User 설정 메서드
    public void setUser(User user) {
        this.user = user;
    }
}
