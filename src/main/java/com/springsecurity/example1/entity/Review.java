package com.springsecurity.example1.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;
    
    @Column(nullable = false)
    private String content;
    
    @Column(nullable = false)
    private int rating; // 1~5점
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    private String imageUrl;
    
    @Builder
    public Review(User user, Product product, OrderItem orderItem, 
                 String content, int rating, String imageUrl) {
        this.user = user;
        this.product = product;
        this.orderItem = orderItem;
        this.content = content;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.createdAt = LocalDateTime.now();
    }
    
    // 리뷰 수정
    public void update(String content, int rating, String imageUrl) {
        this.content = content;
        this.rating = rating;
        if (imageUrl != null) {
            this.imageUrl = imageUrl;
        }
    }
    
    // User 설정 메서드
    public void setUser(User user) {
        this.user = user;
    }
}
