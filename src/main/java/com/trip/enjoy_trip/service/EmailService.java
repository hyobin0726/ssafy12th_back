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
        Random random = new Random();
        int codeLength = 6; // 원하는 코드 길이 설정
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < codeLength; i++) {
            code.append(random.nextInt(10)); // 0부터 9까지의 숫자 추가
        }

        return code.toString();
    }

    // 이메일 내용 설정 (간단한 텍스트)
    private String setTextContent(String code) {
        return "안녕하세요 당신과 나와의 이야기 당나기입니다.\n\n인증 코드: " + code + "\n\n해당 코드를 입력하여 이메일 인증을 완료해주세요.";
    }


    // 이메일 폼 생성
    private MimeMessage createEmailForm(String email) throws MessagingException {
        String authCode = createCode();

        MimeMessage message = javaMailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("이메일 인증 코드입니다.");
        message.setFrom(senderEmail);
        message.setText(setTextContent(authCode), "utf-8");

        // Redis에 인증 코드 저장 (3분 동안 유효)
        redisUtil.setDataExpire(email, authCode, 180L);

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
        String codeFoundByEmail = redisUtil.getData(email);  // Redis에서 인증번호 조회
        if (codeFoundByEmail != null && codeFoundByEmail.equals(code)) {
            // 인증 성공 시 Redis에서 해당 인증번호 삭제 (만료 처리)
            redisUtil.deleteData(email);
            return true;
        }
        return false;
    }


}
