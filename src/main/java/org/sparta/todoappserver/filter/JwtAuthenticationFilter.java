package org.sparta.todoappserver.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.sparta.todoappserver.Dto.user.LoginRequestDto;
import org.sparta.todoappserver.Dto.user.LoginResponseDto;
import org.sparta.todoappserver.entity.User;
import org.sparta.todoappserver.exception.FilterExceptionHandler;
import org.sparta.todoappserver.jwt.JwtUtil;
import org.sparta.todoappserver.repository.UserRepository;
import org.sparta.todoappserver.service.UserService;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j(topic = "Authorization: 로그인 및 JWT 생성")
@Component
//@Order(3)
//@WebFilter("/api/user/login")
public class JwtAuthenticationFilter implements Filter {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
       HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
       HttpServletResponse httpServletResponse = (HttpServletResponse)servletResponse;

        log.info("Filtering request: " + httpServletRequest.getRequestURI());

        LoginRequestDto requestDto = new ObjectMapper().readValue(httpServletRequest.getInputStream(), LoginRequestDto.class);

        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        try {
            //사용자 확인
            User user = userRepository.findByUsername(username).orElseThrow(
                    () -> new IllegalArgumentException("등록된 사용자가 없습니다."));

            //비밀번호 확인
            if (!password.equals(user.getPassword())) {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }

            //JWT 생성 및 HTTP header에 저장
            //access 토큰
            String accessToken = jwtUtil.createAccessToken(user.getUsername(),user.getRole());
            httpServletResponse.addHeader(JwtUtil.ACCESS_AUTHORIZATION_HEADER, accessToken);

            //refresh 토큰
            String refreshToken = jwtUtil.createRefreshToken(user.getUsername(),user.getRole());
            httpServletResponse.addHeader(JwtUtil.REFRESH_AUTHORIZATION_HEADER ,refreshToken);

            log.info("jwt 생성");

        }catch (IllegalArgumentException e){
            FilterExceptionHandler.handleExceptionInFilter(httpServletResponse,e);
            return;
        }

        chain.doFilter(servletRequest, servletResponse); // 다음 Filter 로 이동
    }
}
