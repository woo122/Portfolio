package com.springsecurity.example1.controller;

import com.springsecurity.example1.config.PrincipalDetails;
import com.springsecurity.example1.entity.ProductCategory;
import com.springsecurity.example1.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/products")
public class AdminProductController {

    private final ProductService productService;

    // 관리자용 상품 목록 페이지
    @GetMapping
    public String adminProductList(Model model,
                                   @AuthenticationPrincipal PrincipalDetails principalDetails) {

        if (principalDetails != null) {
            model.addAttribute("userName", principalDetails.getNickname());
            model.addAttribute("isAdmin", principalDetails.isAdmin());
        }

        model.addAttribute("products", productService.findAll());
        model.addAttribute("categories", ProductCategory.values());
        return "admin-products";
    }

    // 상품 등록 처리
    @PostMapping
    public String createProduct(@RequestParam String name,
                                @RequestParam(required = false) String description,
                                @RequestParam int price,
                                @RequestParam("stockQuantity") int stockQuantity,
                                @RequestParam(required = false) String imageUrl,
                                @RequestParam ProductCategory category,
                                @RequestParam(required = false, defaultValue = "false") boolean featured,
                                @RequestParam(required = false, defaultValue = "false") boolean discounted) {

        productService.createProduct(name, description, price, stockQuantity, imageUrl, category, featured, discounted);
        return "redirect:/admin/products";
    }

    // 상품 삭제
    @PostMapping("/{id}/delete")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }

    // 상품 수정 폼
    @GetMapping("/{id}")
    public String editProductForm(@PathVariable Long id,
                                  Model model,
                                  @AuthenticationPrincipal PrincipalDetails principalDetails) {

        if (principalDetails != null) {
            model.addAttribute("userName", principalDetails.getNickname());
            model.addAttribute("isAdmin", principalDetails.isAdmin());
        }

        var product = productService.findById(id);
        model.addAttribute("product", product);
        model.addAttribute("categories", ProductCategory.values());
        return "admin-product-edit";
    }

    // 상품 수정 처리
    @PostMapping("/{id}")
    public String updateProduct(@PathVariable Long id,
                                @RequestParam String name,
                                @RequestParam(required = false) String description,
                                @RequestParam int price,
                                @RequestParam("stockQuantity") int stockQuantity,
                                @RequestParam(required = false) String imageUrl,
                                @RequestParam ProductCategory category,
                                @RequestParam(required = false, defaultValue = "false") boolean featured,
                                @RequestParam(required = false, defaultValue = "false") boolean discounted) {

        productService.updateProduct(id, name, description, price, stockQuantity, imageUrl, category, featured, discounted);
        return "redirect:/admin/products";
    }
}
