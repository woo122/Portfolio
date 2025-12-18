package com.springsecurity.example1.repository;

import com.springsecurity.example1.entity.Cart;
import com.springsecurity.example1.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    
    // 사용자 ID로 장바구니 조회 (장바구니 상품 정보 포함)
    @EntityGraph(attributePaths = {"cartItems", "cartItems.product"})
    Optional<Cart> findByUserId(Long userId);
    
    // 사용자 ID로 장바구니 ID 조회
    @Query("SELECT c.id FROM Cart c WHERE c.user.id = :userId")
    Optional<Long> findIdByUserId(@Param("userId") Long userId);
    
    // 사용자 ID로 장바구니 존재 여부 확인
    boolean existsByUserId(Long userId);
    
    // 사용자 ID로 장바구니 삭제
    void deleteByUserId(Long userId);
}
