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
    public String createAccessToken(String userId) {
        return createToken(userId, accessTokenValidTime);
    }

    // 리프레시 토큰 생성
    public String createRefreshToken(String userId) {
        return createToken(userId, refreshTokenValidTime);
    }

    private String createToken(String userId, long expireTime) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(userId) // loginId를 토큰의 subject로 사용
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
    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

}
