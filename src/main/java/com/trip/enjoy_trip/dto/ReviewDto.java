package com.trip.enjoy_trip.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class ReviewDto {
    private Integer reviewId;  // 리뷰 ID
    private String content;    // 리뷰 내용
    private int point;         // 리뷰 평점
    private int visibility;    // 공개 설정 (0: 전체 공개, 1: 모임)
    private int userId;        // 작성자 ID
    private List<String> imageUrls; // 첨부 이미지 URL 리스트
}
