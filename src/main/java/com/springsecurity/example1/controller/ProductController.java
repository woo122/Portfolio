package com.springsecurity.example1.controller;

import com.springsecurity.example1.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    // 상품 전체 목록
    @GetMapping
    public String productList(Model model) {
        model.addAttribute("products", productService.findAll());
        return "products"; // products.mustache
    }
}
