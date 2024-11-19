package com.trip.enjoy_trip.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttractionDto {
    private Integer attractionId;
    private Integer contentId;
    private String title;
    private Integer contentTypeId;
    private Integer areaCode;
    private Integer siGunGuCode;
    private String firstImage1;
    private String firstImage2;
    private Integer mapLevel;
    private Double  latitude;
    private Double  longitude;
    private String zipcode;
    private String tel;
    private String addr1;
    private String addr2;
    private String businessHours;
    private String homepage;
    private String overview;

    // 추가된 distance 필드
    private Double distance;
}
