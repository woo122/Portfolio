package com.springsecurity.example1.repository;

import com.springsecurity.example1.entity.Product;
import com.springsecurity.example1.entity.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // 카테고리별 상품 조회 (페이징)
    Page<Product> findByCategory(ProductCategory category, Pageable pageable);

    // 상품명 검색
    Page<Product> findByNameContaining(String name, Pageable pageable);

    // 카테고리 + 상품명 검색
    Page<Product> findByCategoryAndNameContaining(ProductCategory category, String name, Pageable pageable);

    // 가격 범위 조회
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice")
    List<Product> findByPriceRange(@Param("minPrice") int minPrice,
                                   @Param("maxPrice") int maxPrice);

    // 재고 있는 상품
    List<Product> findByStockQuantityGreaterThan(int stockQuantity);

    // 카테고리별 인기상품 (평점순)
    @Query("""
        SELECT p FROM Product p
        LEFT JOIN Review r ON p.id = r.product.id
        WHERE p.category = :category
        GROUP BY p.id
        ORDER BY COALESCE(AVG(r.rating), 0) DESC
    """)
    List<Product> findPopularProductsByCategory(@Param("category") ProductCategory category,
                                                Pageable pageable);

    // ID 리스트 조회
    List<Product> findByIdIn(List<Long> ids);

    // 🔥 메인용
    List<Product> findTop4ByFeaturedTrue();

    // 💸 할인상품
    List<Product> findTop4ByDiscountedTrue();
}
