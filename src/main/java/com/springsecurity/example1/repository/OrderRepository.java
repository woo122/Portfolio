package com.springsecurity.example1.repository;

import com.springsecurity.example1.entity.Order;
import com.springsecurity.example1.entity.OrderStatus;
import com.springsecurity.example1.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    
    // 사용자별 주문 목록 조회 (주문 상품 정보 포함)
    @EntityGraph(attributePaths = {"orderItems", "orderItems.product"})
    List<Order> findByUserIdOrderByOrderDateDesc(Long userId);
    
    // 주문 상태별 주문 목록 조회 (페이징 처리)
    Page<Order> findByStatus(OrderStatus status, Pageable pageable);
    
    // 특정 기간 내 주문 목록 조회
    List<Order> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    // 주문 번호로 주문 조회 (주문 상품 정보 포함)
    @EntityGraph(attributePaths = {"orderItems", "orderItems.product"})
    Optional<Order> findWithOrderItemsById(Long id);
    
    // 사용자 ID와 주문 ID로 주문 조회
    Optional<Order> findByIdAndUserId(Long id, Long userId);
    
    // 주문 상태 업데이트
    @Query("UPDATE Order o SET o.status = :status WHERE o.id = :orderId")
    void updateOrderStatus(@Param("orderId") Long orderId, @Param("status") OrderStatus status);
    
    // 특정 사용자의 주문 수 조회
    long countByUserId(Long userId);
    
    // 특정 사용자의 총 주문 금액 조회
    @Query("SELECT SUM(oi.orderPrice * oi.count) FROM Order o JOIN o.orderItems oi WHERE o.user.id = :userId")
    Long getTotalOrderAmountByUserId(@Param("userId") Long userId);
}
