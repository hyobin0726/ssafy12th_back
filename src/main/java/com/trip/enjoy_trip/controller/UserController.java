package com.trip.enjoy_trip.controller;

import com.trip.enjoy_trip.dto.LoginDto;
import com.trip.enjoy_trip.dto.UserDto;
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
    public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto, HttpSession session) {
        try {
            String loginMessage = userService.loginUser(loginDto, session);
            return ResponseEntity.status(HttpStatus.OK).body(loginMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패: " + e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        if (session.getAttribute("user") != null) {
            session.invalidate(); // 세션 무효화
            return ResponseEntity.status(HttpStatus.OK).body("로그아웃에 성공하였습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("이미 로그아웃된 상태입니다.");
        }
    }

}
