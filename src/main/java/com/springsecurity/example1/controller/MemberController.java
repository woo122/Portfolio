package com.springsecurity.example1.controller;

import com.springsecurity.example1.dto.JoinDTO;
import com.springsecurity.example1.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final JoinService joinService;

    @GetMapping("/join")
    public String joinForm() {
        return "join"; // templates/join.mustache
    }

    @PostMapping("/joinProc")
    public String joinProcess(JoinDTO joinDTO) {
        System.out.println("회원가입 요청: " + joinDTO.getLoginId());
        joinService.joinProcess(joinDTO);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login"; // templates/login.mustache
    }

}
