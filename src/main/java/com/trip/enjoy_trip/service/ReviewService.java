package com.trip.enjoy_trip.service;

import com.trip.enjoy_trip.dto.ReviewDto;

import java.util.List;

public interface ReviewService {
    //리뷰
    void createReview(ReviewDto reviewDto);
    ReviewDto getReviewById(Integer reviewId);  // 리뷰 ID로 특정 리뷰 조회
    List<ReviewDto> getAllReviews();           // 전체 리뷰 목록 조회
    void updateReview(ReviewDto reviewDto);    // 리뷰 수정
    void deleteReview(Integer reviewId);       // 리뷰 삭제

    //좋아요
    void likeReview(Integer reviewId, Integer userId); //리뷰 좋아요
    int getLikeCount(Integer reviewId); //좋아요 갯수 조회
    boolean unlikeReview(Integer reviewId, Integer userId); //좋아요 취소
}
