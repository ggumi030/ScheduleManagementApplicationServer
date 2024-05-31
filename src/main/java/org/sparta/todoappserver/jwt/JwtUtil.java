package org.sparta.todoappserver.jwt;

import jakarta.servlet.http.HttpServletResponse;
import org.sparta.todoappserver.entity.UserRoleEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j(topic = "JwtUtil")
@Component
public class JwtUtil {
    // Header KEY 값
    public static final String ACCESS_AUTHORIZATION_HEADER = "Access_Authorization";
    public static final String REFRESH_AUTHORIZATION_HEADER = "Refresh_Authorization";
    // JWT 사용자 권한 값의 KEY
    public static final String AUTHORIZATION_KEY = "auth";
    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer ";
    // 토큰 만료시간
    private final long ACCESS_TOKEN_TIME = 60 * 1000L; // 60분 (기준 ms)
    private final long REFRESH_TOKEN_TIME = 24 * ACCESS_TOKEN_TIME;

    @Value("${jwt.secret.key}") // properties에 저장해둔 Base64 Encode 한 SecretKey 가져오기
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // 토큰 생성 (JWT 생성)
    public String createAccessToken(String username, UserRoleEnum role) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username) // 사용자 식별자값(ID)
                        .claim(AUTHORIZATION_KEY, role) // 사용자 권한
                        .setExpiration(new Date(date.getTime() + ACCESS_TOKEN_TIME)) // 만료 시간
                        .setIssuedAt(date) // 발급일
                        .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                        .compact();
    }

    public String createRefreshToken(String username,UserRoleEnum role){
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username)
                        .claim(AUTHORIZATION_KEY, role) // 사용자 권한
                        .setExpiration(new Date(date.getTime() + REFRESH_TOKEN_TIME))
                        .setIssuedAt(date)
                        .signWith(key,signatureAlgorithm)
                        .compact();
    }

    // header 에서 JWT 가져오기
    public String getAccessJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(ACCESS_AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String getRefreshJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(REFRESH_AUTHORIZATION_HEADER);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String getRoleFromJwt(String refreshTokenValue){
        Claims role = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(refreshTokenValue)
                .getBody();

        return role.get(AUTHORIZATION_KEY, String.class);
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String refreshJWT(HttpServletResponse httpServletResponse, String refreshTokenValue, String accessTokenValue) throws IOException {
        //유저정보 가져오기
        Claims info = getUserInfoFromToken(refreshTokenValue);
        //유저 role 정보 가져오기
        String string_role = getRoleFromJwt(refreshTokenValue);

        UserRoleEnum role = UserRoleEnum.USER;

        if(!"USER".equals(string_role)){
            role = UserRoleEnum.ADMIN;
        }

        //JWT 생성 및 HTTP header에 저장
        //access 토큰
        accessTokenValue = createAccessToken(info.getSubject(),role);
        httpServletResponse.addHeader(JwtUtil.ACCESS_AUTHORIZATION_HEADER, accessTokenValue);

        //refresh 토큰
        refreshTokenValue = createRefreshToken(info.getSubject(),role);
        httpServletResponse.addHeader(JwtUtil.REFRESH_AUTHORIZATION_HEADER ,refreshTokenValue);

        return accessTokenValue = accessTokenValue.substring(7);
    }
}