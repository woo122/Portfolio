package com.springsecurity.example1.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();
    
    // 생성 메서드
    public static Cart createCart(User user) {
        Cart cart = new Cart();
        cart.user = user;
        return cart;
    }
    
    // 장바구니에 상품 추가
    public void addCartItem(CartItem cartItem) {
        cartItems.add(cartItem);
        cartItem.setCart(this);
    }
    
    // 장바구니 비우기
    public void clear() {
        cartItems.clear();
    }
    
    // 장바구니 전체 금액 계산
    public int getTotalPrice() {
        return cartItems.stream()
                .mapToInt(CartItem::getTotalPrice)
                .sum();
    }
    
    // User 설정 메서드
    public void setUser(User user) {
        this.user = user;
    }
    
    // 장바구니에서 주문 생성
    public Order createOrder() {
        // 주문 생성 로직 (추후 구현)
        return null;
    }
}
