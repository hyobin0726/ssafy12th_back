package com.trip.enjoy_trip.controller;

import com.trip.enjoy_trip.dto.ReviewDto;
import com.trip.enjoy_trip.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/reviews")  // URL에 맞춰 기본 경로 수정
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/write")
    public ResponseEntity<String> createReview(@Valid @RequestBody ReviewDto reviewDto) {
        reviewService.createReview(reviewDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("리뷰가 성공적으로 작성되었습니다.");
//        try {
//            reviewService.createReview(reviewDto);
//            return ResponseEntity.status(HttpStatus.CREATED).body("리뷰가 성공적으로 작성되었습니다.");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("리뷰 작성에 실패하였습니다.");
//        }
    }
}
