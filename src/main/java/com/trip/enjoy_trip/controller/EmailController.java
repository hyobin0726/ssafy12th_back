package com.trip.enjoy_trip.controller;

import com.trip.enjoy_trip.repository.EmailRepository;
import com.trip.enjoy_trip.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.mail.MessagingException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/email")
public class EmailController {

    private final EmailService emailService;

    // 인증 코드 전송
    @PostMapping("/auth")
    public ResponseEntity<String> sendVerificationCode(@RequestParam String email) {
        try {
            emailService.sendEmail(email);
            return ResponseEntity.status(HttpStatus.OK).body("인증 코드가 이메일로 전송되었습니다.");
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이메일 전송에 실패했습니다.");
        }
    }

    // 인증 코드 확인
    @PostMapping("/auth/verify")
    public ResponseEntity<String> verifyCode(@RequestParam String email, @RequestParam String code) {
        boolean isValid = emailService.verifyEmailCode(email, code);

        if (isValid) {
            return ResponseEntity.status(HttpStatus.OK).body("인증이 완료되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 코드가 올바르지 않습니다.");
        }
    }
}
