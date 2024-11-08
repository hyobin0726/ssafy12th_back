package com.trip.enjoy_trip.service;

import com.trip.enjoy_trip.dto.TokenDto;
import com.trip.enjoy_trip.exception.InvalidTokenException;
import com.trip.enjoy_trip.security.JwtTokenProvider;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class JwtTokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    public JwtTokenService(JwtTokenProvider jwtTokenProvider, RedisTemplate<String, String> redisTemplate) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.redisTemplate = redisTemplate;
    }

    public TokenDto generateTokens(Integer userId) {
        String accessToken = jwtTokenProvider.createAccessToken(userId);
        String refreshToken = jwtTokenProvider.createRefreshToken(userId);

        // Redis에 리프레시 토큰 저장
        redisTemplate.opsForValue().set("refreshToken:" + userId, refreshToken, 7, TimeUnit.DAYS);

        return new TokenDto(accessToken, refreshToken);
    }
    // 리프레시 토큰 삭제 메서드
    public void deleteRefreshToken(Integer userId) {
        String key = "refreshToken:" + userId;
        redisTemplate.delete(key); // Redis에서 해당 리프레시 토큰 삭제
    }
    // 리프레시 토큰을 사용하여 새로운 토큰을 발급하는 메서드
    public TokenDto refreshTokens(String refreshToken) {
        // 리프레시 토큰의 유효성 검증
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new InvalidTokenException("유효하지 않은 리프레시 토큰입니다.");
        }

        // 리프레시 토큰에서 userId 추출
        Integer  userId = jwtTokenProvider.getUserIdFromToken(refreshToken);

        // Redis에서 저장된 리프레시 토큰과 일치하는지 확인
        String storedRefreshToken = redisTemplate.opsForValue().get("refreshToken:" + userId);
        if (!refreshToken.equals(storedRefreshToken)) {
            throw new InvalidTokenException("리프레시 토큰이 일치하지 않습니다.");
        }

        // 새로운 액세스 토큰 및 리프레시 토큰 생성
        String newAccessToken = jwtTokenProvider.createAccessToken(userId);
        String newRefreshToken = jwtTokenProvider.createRefreshToken(userId);

        // 기존 리프레시 토큰을 삭제하고 새 리프레시 토큰을 Redis에 저장
        redisTemplate.opsForValue().set("refreshToken:" + userId, newRefreshToken, 7, TimeUnit.DAYS);

        // 새롭게 생성된 토큰 반환
        return new TokenDto(newAccessToken, newRefreshToken);
    }
}
