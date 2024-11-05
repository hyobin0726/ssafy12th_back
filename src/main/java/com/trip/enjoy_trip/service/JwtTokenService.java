package com.trip.enjoy_trip.service;

import com.trip.enjoy_trip.dto.TokenDto;
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

    public TokenDto generateTokens(String loginId) {
        String accessToken = jwtTokenProvider.createAccessToken(loginId);
        String refreshToken = jwtTokenProvider.createRefreshToken(loginId);

        // Redis에 리프레시 토큰 저장
        redisTemplate.opsForValue().set("refreshToken:" + loginId, refreshToken, 7, TimeUnit.DAYS);

        return new TokenDto(accessToken, refreshToken);
    }
    // 리프레시 토큰 삭제 메서드
    public void deleteRefreshToken(String loginId) {
        String key = "refreshToken:" + loginId;
        redisTemplate.delete(key); // Redis에서 해당 리프레시 토큰 삭제
    }
}
