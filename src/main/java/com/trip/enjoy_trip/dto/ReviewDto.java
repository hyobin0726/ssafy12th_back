package com.trip.enjoy_trip.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class ReviewDto {
    private Integer reviewId;  // 리뷰 ID
    private String content;    // 리뷰 내용

    @NotNull(message = "Point 가 없습니다.")
    private Integer point;         // 리뷰 평점

    @NotNull(message = "Visibility 가 없습니다.")
    private Integer visibility;    // 공개 설정 (0: 전체 공개, 1: 모임)

    @NotNull(message = "User ID 가 없습니다.")
    private Integer userId;        // 작성자 ID

    @NotNull(message = "imageUrls 가 없습니다.")
    private List<String> imageUrls; // 첨부 이미지 URL 리스트
    private String attractionId; // 추가된 필드
    private int gugunId;        // 추가된 필드
    private int gugunSidoId;    // 추가된 필드
}
