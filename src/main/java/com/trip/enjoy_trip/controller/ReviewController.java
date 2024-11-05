package com.trip.enjoy_trip.controller;

import com.trip.enjoy_trip.dto.ReviewDto;
import com.trip.enjoy_trip.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

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

    // 특정 리뷰 조회 (리뷰 ID로 조회)
    @GetMapping("/list/{reviewId}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable Integer reviewId) {
        ReviewDto review = reviewService.getReviewById(reviewId);
        return ResponseEntity.ok(review);
    }

    // 전체 리뷰 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<ReviewDto>> getAllReviews() {
        List<ReviewDto> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }

    // 리뷰 수정
    @PutMapping("/modify/{reviewId}")
    public ResponseEntity<String> updateReview(
            @PathVariable Integer reviewId,
            @Valid @RequestBody ReviewDto reviewDto) {

        // 리뷰 ID 설정
        reviewDto.setReviewId(reviewId);
        reviewService.updateReview(reviewDto);
        return ResponseEntity.ok("리뷰가 성공적으로 수정되었습니다.");
    }

    //리뷰 삭제
    @DeleteMapping("/remove/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Integer reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok("리뷰가 성공적으로 삭제되었습니다.");
    }
}
