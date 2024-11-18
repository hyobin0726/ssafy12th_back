package com.trip.enjoy_trip.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CrewDto {
    private Integer crewId;
    private String name;
    private String introduction;
    private String profileUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
