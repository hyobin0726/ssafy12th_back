package com.trip.enjoy_trip.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private Integer reviewId;  // 리뷰 ID
    private String content;    // 리뷰 내용

    @NotNull(message = "Point 가 없습니다.")
    private Integer point;         // 리뷰 평점

    @NotNull(message = "Visibility 가 없습니다.")
    private Integer visibility;    // 공개 설정 (0: 전체 공개, 1: 모임)

//    @NotNull(message = "User ID 가 없습니다.")
    private Integer userId;        // 작성자 ID (토큰에서 설정)

    @NotNull(message = "imageUrls 가 없습니다.")
    private List<String> imageUrls; // 첨부 이미지 URL 리스트
    private Integer attractionId; // 추가된 필드 (타입 변경)
    private Integer gugunId;      // 추가된 필드 (타입 변경)
    private Integer gugunSidoId;  // 추가된 필드 (타입 변경)
    private LocalDateTime createdAt;  // 작성 시간 필드 추가
}
