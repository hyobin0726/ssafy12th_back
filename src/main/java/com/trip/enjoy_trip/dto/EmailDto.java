package com.trip.enjoy_trip.dto;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
public class EmailDto {

    @NotBlank(message = "이메일 주소를 입력해주세요.")
    @Email(message = "유효한 이메일 주소를 입력해주세요.")
    private String mail;         // 이메일 주소

    @NotBlank(message = "인증 코드를 입력해주세요.")
    private String verifyCode;    // 인증 코드
}
