package com.trip.enjoy_trip.controller;

import com.trip.enjoy_trip.dto.UserDto;
import com.trip.enjoy_trip.security.JwtTokenProvider;
import com.trip.enjoy_trip.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("api/v1/member")
@RestController
public class MemberController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    @GetMapping
    public ResponseEntity<UserDto> getMyPage(@RequestHeader("Authorization") String token) {
        String confirmToken = token.replace("Bearer ", "");

        // 토큰 유효성 검사
        if (!jwtTokenProvider.validateToken(confirmToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        // 토큰에서 userId 추출
        Integer userId = jwtTokenProvider.getUserIdFromToken(confirmToken);

        // userId로 사용자 정보 조회
        UserDto userInfo = userService.getUserInfo(userId);
        return ResponseEntity.ok(userInfo);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getOtherUserProfile(@PathVariable Integer userId) {
        UserDto userProfile = userService.getOtherUserProfile(userId);
        return ResponseEntity.ok(userProfile);
    }
}
