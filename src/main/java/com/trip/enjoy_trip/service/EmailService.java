package com.trip.enjoy_trip.service;
import com.trip.enjoy_trip.common.util.RedisUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final RedisUtil redisUtil;
    private static final String senderEmail = "sanbyul1@naver.com";

    // 인증 코드 생성
    private String createCode() {
        int leftLimit = 48; // '0'
        int rightLimit = 122; // 'z'
        int targetStringLength = 6;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    // 이메일 내용 설정 (간단한 텍스트)
    private String setTextContent(String code) {
        return "안녕하세요.\n\n인증 코드: " + code + "\n\n해당 코드를 입력하여 이메일 인증을 완료해 주세요.";
    }

    // 이메일 폼 생성
    private MimeMessage createEmailForm(String email) throws MessagingException {
        String authCode = createCode();

        MimeMessage message = javaMailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("이메일 인증 코드입니다.");
        message.setFrom(senderEmail);
        message.setText(setTextContent(authCode), "utf-8");

        // Redis에 인증 코드 저장 (30분 동안 유효)
        redisUtil.setDataExpire(email, authCode, 60 * 30L);

        return message;
    }

    // 인증 코드 이메일 발송
    public void sendEmail(String toEmail) throws MessagingException {
        // 기존 인증 코드가 있으면 삭제 후 새로 생성
        if (redisUtil.existData(toEmail)) {
            redisUtil.deleteData(toEmail);
        }
        MimeMessage emailForm = createEmailForm(toEmail);
        javaMailSender.send(emailForm);
    }

    // 코드 검증
    public Boolean verifyEmailCode(String email, String code) {
        String codeFoundByEmail = redisUtil.getData(email);
        log.info("code found by email: " + codeFoundByEmail);
        return codeFoundByEmail != null && codeFoundByEmail.equals(code);
    }
}
