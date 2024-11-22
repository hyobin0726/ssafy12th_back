package com.trip.enjoy_trip.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CrewDto {
    private Integer crewId;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CrewUserDto> users;
    private String loginId;
}
