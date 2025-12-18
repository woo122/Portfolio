package com.springsecurity.example1.service;

import com.springsecurity.example1.dto.JoinDTO;
import com.springsecurity.example1.entity.User;
import com.springsecurity.example1.entity.UserRole;
import com.springsecurity.example1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class JoinService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;  // BCryptPasswordEncoder 대신 PasswordEncoder 주입
    @Transactional
    public void joinProcess(JoinDTO joinDTO) {
        if (userRepository.existsByLoginId(joinDTO.getLoginId())) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
        String encPassword = passwordEncoder.encode(joinDTO.getPassword());  // PasswordEncoder 사용
        User user = User.builder()
                .loginId(joinDTO.getLoginId())
                .password(encPassword)
                .nickname(joinDTO.getNickname())
                .email(joinDTO.getEmail())
                .role(UserRole.USER)
                .build();
        userRepository.save(user);
    }
}