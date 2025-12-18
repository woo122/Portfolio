package com.springsecurity.example1.service;

import com.springsecurity.example1.entity.Product;
import com.springsecurity.example1.entity.ProductCategory;
import com.springsecurity.example1.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // 전체 상품 (이미 있음)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. id=" + id));
    }

    // 🔥 인기상품 3개
    public List<Product> findFeaturedProducts() {
        return productRepository.findTop4ByFeaturedTrue();
    }

    // 💸 할인상품 3개 (❗ 이게 없어서 에러 났던 것)
    public List<Product> findDiscountedProducts() {
        return productRepository.findTop4ByDiscountedTrue();
    }
    
    // 관리자용 상품 생성
    @Transactional
    public Product createProduct(String name, String description, int price,
                                 int stockQuantity, String imageUrl, ProductCategory category,
                                 boolean featured, boolean discounted) {
        Product product = Product.builder()
                .name(name)
                .description(description)
                .price(price)
                .stockQuantity(stockQuantity)
                .imageUrl(imageUrl)
                .category(category)
                .build();

        // featured / discounted 는 빌더 이후에 설정
        try {
            var featuredField = Product.class.getDeclaredField("featured");
            featuredField.setAccessible(true);
            featuredField.set(product, featured);

            var discountedField = Product.class.getDeclaredField("discounted");
            discountedField.setAccessible(true);
            discountedField.set(product, discounted);
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
        }

        return productRepository.save(product);
    }

    // 관리자용 상품 삭제
    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // 관리자용 상품 수정
    @Transactional
    public void updateProduct(Long id, String name, String description, int price,
                              int stockQuantity, String imageUrl, ProductCategory category,
                              boolean featured, boolean discounted) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. id=" + id));

        product.update(name, description, price, stockQuantity, imageUrl, category);

        try {
            var featuredField = Product.class.getDeclaredField("featured");
            featuredField.setAccessible(true);
            featuredField.set(product, featured);

            var discountedField = Product.class.getDeclaredField("discounted");
            discountedField.setAccessible(true);
            discountedField.set(product, discounted);
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
        }
    }

}
