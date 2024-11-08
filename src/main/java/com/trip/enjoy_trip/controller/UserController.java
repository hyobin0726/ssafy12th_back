package com.trip.enjoy_trip.controller;

import com.trip.enjoy_trip.dto.LoginDto;
import com.trip.enjoy_trip.dto.TokenDto;
import com.trip.enjoy_trip.dto.UserDto;
import com.trip.enjoy_trip.security.JwtTokenProvider;
import com.trip.enjoy_trip.service.JwtTokenService;
import com.trip.enjoy_trip.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
@RestController
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenService jwtTokenService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }

        userService.join(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다.");
    }

    @GetMapping("/duplication")
    public ResponseEntity<String> checkLoginIdDuplicate(@RequestParam("login_id") String loginId) {
        boolean isDuplicate = userService.checkLoginId(loginId);

        if (isDuplicate) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 사용 중인 아이디입니다.");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("사용 가능한 아이디입니다.");
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
        try {
            // 로그인 후 JWT 토큰을 발급
            TokenDto tokenDto = userService.loginUser(loginDto);
            return ResponseEntity.status(HttpStatus.OK).body(tokenDto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패: " + e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String tokenHeader) {
        // "Bearer "를 제거하여 순수 토큰 값만 추출
        String token = tokenHeader.replace("Bearer ", "");

        try {
            // JWT에서 사용자 ID 또는 loginId를 추출하여 Redis에서 리프레시 토큰 삭제
            Integer userId = jwtTokenProvider.getUserIdFromToken(token);
            userService.logout(userId);

            return ResponseEntity.status(HttpStatus.OK).body("로그아웃에 성공하였습니다.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로그아웃 실패: " + e.getMessage());
        }
    }
    @PostMapping("/re-token")
    public ResponseEntity<TokenDto> refreshAccessToken(@RequestBody TokenDto tokenDto) {
        String refreshToken = tokenDto.getRefreshToken();

        // 재발급을 JwtTokenService에 위임
        TokenDto newTokens = jwtTokenService.refreshTokens(refreshToken);

        return ResponseEntity.ok(newTokens);
    }


}
