package com.springsecurity.example1.controller;

import com.springsecurity.example1.config.PrincipalDetails;
import com.springsecurity.example1.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ProductService productService;

    @GetMapping("/")
    public String index(Model model,
                        @AuthenticationPrincipal PrincipalDetails principalDetails) {

        if (principalDetails != null) {
            model.addAttribute("userName", principalDetails.getNickname());
            model.addAttribute("isAdmin", principalDetails.isAdmin());
        }

        model.addAttribute("featuredProducts",
                productService.findFeaturedProducts());

        model.addAttribute("discountedProducts",
                productService.findDiscountedProducts());

        return "index";
}

    @GetMapping("/intro")
    public String intro(Model model,
                        @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (principalDetails != null) {
            model.addAttribute("userName", principalDetails.getNickname());
            model.addAttribute("isAdmin", principalDetails.isAdmin());
        }
        return "intro";
    }

    @GetMapping("/order")
    public String order(Model model,
                        @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (principalDetails != null) {
            model.addAttribute("userName", principalDetails.getNickname());
            model.addAttribute("isAdmin", principalDetails.isAdmin());
        }
        return "order";
    }

    @GetMapping("/admin")
    public String admin(Model model,
                        @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (principalDetails != null) {
            model.addAttribute("userName", principalDetails.getNickname());
            model.addAttribute("isAdmin", principalDetails.isAdmin());
        }
        return "admin";
    }
    @GetMapping("/db")
    @ResponseBody
    public Object dbCheck() {
        return productService.findDiscountedProducts();
    }
}
