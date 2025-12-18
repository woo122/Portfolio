package com.springsecurity.example1.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    @Column(nullable = false)
    private int orderPrice; // 주문 가격
    
    @Column(nullable = false)
    private int count; // 주문 수량
    
    @Builder
    public OrderItem(Product product, int count, int orderPrice) {
        this.product = product;
        this.count = count;
        this.orderPrice = orderPrice;
        
        // 재고 감소
        product.removeStock(count);
    }
    
    // 주문 상품 생성
    public static OrderItem createOrderItem(Product product, int count) {
        return OrderItem.builder()
                .product(product)
                .count(count)
                .orderPrice(product.getPrice())
                .build();
    }
    
    // 주문 취소 시 재고 복구
    public void cancel() {
        getProduct().addStock(count);
    }
    
    // 주문 상품 전체 가격 조회
    public int getTotalPrice() {
        return orderPrice * count;
    }
    
    // 연관관계 메서드
    public void setOrder(Order order) {
        this.order = order;
    }
}
