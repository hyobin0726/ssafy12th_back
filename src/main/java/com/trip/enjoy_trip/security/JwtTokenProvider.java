package com.trip.enjoy_trip.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256); // 실제 사용 시 보안을 위해 환경 변수로 관리
    private final long accessTokenValidTime = 1000L * 60 * 30; // 30분
    private final long refreshTokenValidTime = 1000L * 60 * 60 * 24 * 7; // 7일

    // 액세스 토큰 생성
    public String createAccessToken(String loginId) {
        return createToken(loginId, accessTokenValidTime);
    }

    // 리프레시 토큰 생성
    public String createRefreshToken(String loginId) {
        return createToken(loginId, refreshTokenValidTime);
    }

    private String createToken(String loginId, long expireTime) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(loginId) // loginId를 토큰의 subject로 사용
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expireTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    // 토큰에서 사용자 loginId 추출
    public String getLoginIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject(); // subject에 loginId가 저장되었다고 가정
    }

}
