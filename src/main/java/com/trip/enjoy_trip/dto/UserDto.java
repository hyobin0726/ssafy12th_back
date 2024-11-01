package com.trip.enjoy_trip.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class UserDto {
    private String loginId;
    private String name;
    private String email;
    private String password;
    private String phone;
    private LocalDate birth;
    private String profileUrl;
    private String oneLiner;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
