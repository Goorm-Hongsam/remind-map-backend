package com.backend.remindmap.Config.JWT;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RequiredArgsConstructor
@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Value("${jwt.secret}")
    private String jwtSecretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("jwt 필터");

        log.info("시크릿키={}",jwtSecretKey);

        String jwtHeader = request.getHeader("Authorization");

        log.info("헤더에서 들어온 jwtHeader={}",jwtHeader);

        // header 가 정상적인 형식인지 확인
        if (jwtHeader == null || !jwtHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // jwt 토큰을 검증해서 정상적인 사용자인지 확인
        String token = jwtHeader.replace("Bearer ", "");

        // 토큰 유효성 검사
        if (!jwtTokenProvider.validateToken(token)) {
            log.info("JwtRequestFilter에서 유효성 검사 실패");
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "잘못된 토큰입니다.");
            return;
        }

        request.setAttribute("member", jwtTokenProvider.getMemberByAccessToken(token));

        filterChain.doFilter(request, response);
    }
}
