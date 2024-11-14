package com.trip.enjoy_trip.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Integer commentId; // 댓글 ID

    @NotNull(message = "content 가 없습니다.")
    private String content; // 댓글 내용
    private Integer reviewId; // 리뷰 ID
    private Integer userId; // 작성자 ID (토큰에서 설정)
    private LocalDateTime createdAt; // 작성 시간 필드 추가
}
