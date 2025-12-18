package com.springsecurity.example1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Primary;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    @Primary
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            // ✅ 개발 단계: CSRF 비활성화
            .csrf(csrf -> csrf.disable())

            // ✅ 접근 권한 설정
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/",                 // 메인
                    "/login",            // 로그인 페이지
                    "/join",             // 회원가입 페이지
                    "/joinProc",         // 회원가입 처리
                    "/css/**",
                    "/js/**",
                    "/images/**"
                ).permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )

            // ✅ 로그인 설정 (핵심)
            .formLogin(login -> login
                .loginPage("/login")          // GET 로그인 페이지
                .loginProcessingUrl("/login") // POST 로그인 처리 (HTML form action)
                .usernameParameter("username")// HTML input name
                .passwordParameter("password")
                .defaultSuccessUrl("/", true) // 로그인 성공 시 메인 이동
                .failureUrl("/login?error")
                .permitAll()
            )

            // ✅ 로그아웃
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            );

        return http.build();
    }
}
