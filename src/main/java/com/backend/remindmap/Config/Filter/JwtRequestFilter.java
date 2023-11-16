package com.backend.remindmap.Config.Filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.backend.remindmap.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RequiredArgsConstructor
@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String jwtSecretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("jwt 필터");

        log.info("시크릿키={}", jwtSecretKey);

        String jwtHeader = ((HttpServletRequest) request).getHeader("Authorization");

        // header 가 정상적인 형식인지 확인
        if (jwtHeader == null || !jwtHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // jwt 토큰을 검증해서 정상적인 사용자인지 확인
        String token = jwtHeader.replace("Bearer ", "");

        Long memberId = null;
        String nickname = null;
        String thumbnailImageUrl = null;

        try {
            log.info("try");
            memberId = JWT.require(Algorithm.HMAC512(jwtSecretKey)).build().verify(token)
                    .getClaim("id").asLong();
            log.info("memberId={}", memberId);
            nickname = JWT.require(Algorithm.HMAC512(jwtSecretKey)).build().verify(token)
                    .getClaim("nickname").asString();
            log.info(nickname);
            thumbnailImageUrl = JWT.require(Algorithm.HMAC512(jwtSecretKey)).build().verify(token)
                    .getClaim("thumbnailImageUrl").asString();
            log.info(thumbnailImageUrl);

        } catch (TokenExpiredException e) {
            log.info("토큰이 만료되었습니다.");
//            e.printStackTrace();
//            request.setAttribute("Authorization", "토큰이 만료되었습니다.");
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "토큰이 만료되었습니다.");
            return;
        } catch (JWTVerificationException e) {
            log.info("유효하지 않은 토큰입니다.");
//            e.printStackTrace();
//            request.setAttribute("Authorization", "유효하지 않은 토큰입니다.");
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "유효하지 않은 토큰입니다.");
            return;
        }

        request.setAttribute("member", new Member(memberId, nickname, thumbnailImageUrl));

        filterChain.doFilter(request, response);
    }
}
