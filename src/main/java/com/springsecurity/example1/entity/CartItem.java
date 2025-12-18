package com.springsecurity.example1.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    @Column(nullable = false)
    private int count;
    
    // 생성 메서드
    public static CartItem createCartItem(Cart cart, Product product, int count) {
        CartItem cartItem = new CartItem();
        cartItem.cart = cart;
        cartItem.product = product;
        cartItem.count = count;
        return cartItem;
    }
    
    // 수량 증가
    public void addCount(int count) {
        this.count += count;
    }
    
    // 수량 업데이트
    public void updateCount(int count) {
        this.count = count;
    }
    
    // 장바구니 상품 가격 조회
    public int getPrice() {
        return product.getPrice();
    }
    
    // 장바구니 상품 전체 가격 조회
    public int getTotalPrice() {
        return getPrice() * count;
    }
    
    // 연관관계 메서드
    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
