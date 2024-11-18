package com.trip.enjoy_trip.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;


@Setter
@Getter
@Builder
@AllArgsConstructor
public class UserDto {
    private Integer userId;
    @NotBlank(message = "아이디를 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9]{8,20}$", message = "아이디는 영어 또는 숫자로 8 ~ 20자리까지 가능합니다.")
    private String loginId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야 합니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$",
            message = "비밀번호는 영문자, 숫자, 특수문자를 포함하여 8~20자여야 합니다."
    )
    private String password;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "유효하지 않은 이메일 형식입니다.")
    private String email;

    @NotBlank(message = "휴대폰 번호를 입력해주세요.")
    @Pattern(regexp = "^01[0-9]-\\d{3,4}-\\d{4}$", message = "유효하지 않은 휴대폰 번호 형식입니다.")
    private String phone;

    @NotNull(message = "생년월일을 입력해주세요.")
    private LocalDate birth;

    private String profileUrl;

    @Size(max = 255, message = "자기소개는 최대 255자까지 입력 가능합니다.")
    private String oneLiner;

}
