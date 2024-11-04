package com.trip.enjoy_trip.service;

import com.trip.enjoy_trip.dto.ReviewDto;
import com.trip.enjoy_trip.repository.ReviewRepository;
import com.trip.enjoy_trip.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void createReview(ReviewDto reviewDto) {
        // imageUrls가 비어 있을 경우 예외 처리
        if (reviewDto.getImageUrls() == null || reviewDto.getImageUrls().isEmpty()) {
            throw new IllegalArgumentException("이미지 URL이 필요합니다.");
        }

        // 리뷰 저장
        reviewRepository.insertReview(reviewDto);
        // 리뷰 ID를 가져와 리뷰 이미지 추가
        reviewRepository.insertReviewImages(reviewDto.getReviewId(), reviewDto.getImageUrls());

    }
}
