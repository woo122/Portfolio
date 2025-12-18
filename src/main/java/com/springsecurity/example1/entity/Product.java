package com.springsecurity.example1.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "products")
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(nullable = false)
    private int price;
    
    @Column(nullable = false)
    private int stockQuantity;
    
    private String imageUrl;
    
    @Enumerated(EnumType.STRING)
    private ProductCategory category;
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();
    
    @Builder
    public Product(String name, String description, int price, int stockQuantity, 
                  String imageUrl, ProductCategory category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.imageUrl = imageUrl;
        this.category = category;
    }
    
    // 상품 수정 메서드
    public void update(String name, String description, int price, int stockQuantity, 
                      String imageUrl, ProductCategory category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.imageUrl = imageUrl;
        this.category = category;
    }
    
    // 재고 감소
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new RuntimeException("상품의 재고가 부족합니다.");
        }
        this.stockQuantity = restStock;
    }
    
    // 재고 증가 (주문 취소 시)
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }
    @Column(nullable = false)
    private boolean featured; // 인기상품(메인 노출)

    @Column(nullable = false)
    private boolean discounted; // 할인상품
}
