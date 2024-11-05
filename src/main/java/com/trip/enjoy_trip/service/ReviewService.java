package com.trip.enjoy_trip.service;

import com.trip.enjoy_trip.dto.ReviewDto;

import java.util.List;

public interface ReviewService {
    void createReview(ReviewDto reviewDto);
    ReviewDto getReviewById(Integer reviewId);  // 리뷰 ID로 특정 리뷰 조회
    List<ReviewDto> getAllReviews();           // 전체 리뷰 목록 조회
    void updateReview(ReviewDto reviewDto);    // 리뷰 수정
}
