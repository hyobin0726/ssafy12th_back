package com.trip.enjoy_trip.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CrewUserDto {
    private Integer crewUserId;
    private LocalDateTime joinedAt;
    private Integer crewId;
    private Integer userId;
    private String loginId;
}
