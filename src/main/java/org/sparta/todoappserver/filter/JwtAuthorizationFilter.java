package org.sparta.todoappserver.filter;


import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.sparta.todoappserver.entity.User;
import org.sparta.todoappserver.exception.FilterExceptionHandler;
import org.sparta.todoappserver.jwt.JwtUtil;
import org.sparta.todoappserver.repository.UserRepository;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j(topic = "AuthenticationFilter")
@Component
//@Order(2)
public class JwtAuthorizationFilter implements Filter {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public JwtAuthorizationFilter(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String url = httpServletRequest.getRequestURI();

        log.info(url);

        if (StringUtils.hasText(url) &&
                (url.startsWith("/api/user") || url.startsWith("/api/schedule"))
        ) {
            // 회원가입, 로그인 관련 API, 일정 관련 API는 인증 필요없이 요청 진행
            //
        } else {
            // 나머지 API 요청은 인증 처리 진행 (comment API)
            // 토큰 확인
            String tokenValue = jwtUtil.getJwtFromHeader(httpServletRequest);

            try {
                if (StringUtils.hasText(tokenValue)) { // 토큰이 존재하면 검증 시작

                    // 토큰 검증
                    if (!jwtUtil.validateToken(tokenValue)) {
                        throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
                    }

                    // 토큰에서 사용자 정보 가져오기
                    Claims info = jwtUtil.getUserInfoFromToken(tokenValue);

                    //회원 DB에 존재하는지 확인
                    User user = userRepository.findByUsername(info.getSubject()).orElseThrow(() ->
                            new NullPointerException("회원가입 하지 않은 사용자입니다.")
                    );

                    request.setAttribute("user", user); //인증완료된 User 객체 request에 담기
                    //
                } else {
                    throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
                }
            } catch (IllegalArgumentException | NullPointerException e) {
                FilterExceptionHandler.handleExceptionInFilter((HttpServletResponse) response, e);
                return;
            }
        }

        chain.doFilter(request, response); // 다음 Filter 로 이동

    }

}