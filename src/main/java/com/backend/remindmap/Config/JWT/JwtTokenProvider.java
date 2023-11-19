package com.backend.remindmap.Config.JWT;

import com.backend.remindmap.member.domain.Member;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import static io.jsonwebtoken.Jwts.parserBuilder;

@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecretKey;

    public String createAccessToken(Member member) {

        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam("type", "jwt")
                .claim("id", member.getMemberId())
                .claim("nickname", member.getNickname())
                .claim("thumbnailImageUrl", member.getThumbnailImageUrl())
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 30)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

    }

    public String createRefreshToken(Member member) {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam("type", "jwt")
                .claim("id",member.getMemberId())
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis() + (1000*60*60*24*14)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 모든 token에 대한 사용자 속성정보 조회
    public Member getMemberByAccessToken(String token) {
        Claims claims =  parserBuilder()
                .setSigningKey(jwtSecretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Long memberId = (Long) claims.get("id");
        String nickname = (String) claims.get("nickname");
        String thumbnailImageUrl = (String) claims.get("thumbnailImageUrl");

        return new Member(memberId, nickname, thumbnailImageUrl);

    }

    // 토큰에서 memberId 조회
    public Long getMemberIdByRefreshToken(String refreshToken) {

        Claims claims =  parserBuilder()
                .setSigningKey(jwtSecretKey)
                .build()
                .parseClaimsJws(refreshToken)
                .getBody();

        Long memberId = (Long) claims.get("id");

        return memberId;
    }

    public boolean validateToken(String token) {

        if (token == null) {
            log.info("Token is null");
            return false;
        }

        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtSecretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

}
