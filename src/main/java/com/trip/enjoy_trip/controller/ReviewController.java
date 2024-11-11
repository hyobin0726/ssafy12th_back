package com.trip.enjoy_trip.controller;

import com.trip.enjoy_trip.dto.ReviewDto;
import com.trip.enjoy_trip.dto.TokenDto;
import com.trip.enjoy_trip.security.JwtTokenProvider;
import com.trip.enjoy_trip.service.JwtTokenService;
import com.trip.enjoy_trip.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")  // URL에 맞춰 기본 경로 수정
public class ReviewController {

    private final ReviewService reviewService;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenService jwtTokenService;

    @Autowired
    public ReviewController(ReviewService reviewService, JwtTokenProvider jwtTokenProvider, JwtTokenService jwtTokenService) {
        this.reviewService = reviewService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtTokenService = jwtTokenService;
    }

    //리뷰 작성
    @PostMapping("/write")
    public ResponseEntity<String> createReview(@Valid @RequestBody ReviewDto reviewDto, @RequestHeader("Authorization") String token) {
        String confirmToken = token.replace("Bearer ", "");

        if (!jwtTokenProvider.validateToken(confirmToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
        }

        // 토큰에서 userId 추출
        Integer userId = jwtTokenProvider.getUserIdFromToken(confirmToken);

        // userId를 ReviewDto에 설정
        reviewDto.setUserId(userId);

        reviewService.createReview(reviewDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("리뷰가 성공적으로 작성되었습니다.");
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
            @Valid @RequestBody ReviewDto reviewDto, @RequestHeader("Authorization") String token) {

        String confirmToken = token.replace("Bearer ", "");

        if(!jwtTokenProvider.validateToken(confirmToken)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
        }

        // 리뷰 ID 설정
        reviewDto.setReviewId(reviewId);
        reviewService.updateReview(reviewDto);
        return ResponseEntity.ok("리뷰가 성공적으로 수정되었습니다.");
    }

    //리뷰 삭제
    @DeleteMapping("/remove/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Integer reviewId, @RequestHeader("Authorization") String token) {
        String confirmToken = token.replace("Bearer ", "");

        if(!jwtTokenProvider.validateToken(confirmToken)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
        }

        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok("리뷰가 성공적으로 삭제되었습니다.");
    }

    // 좋아요 누르기 (POST)
    @PostMapping("/love/{reviewId}")
    public ResponseEntity<String> likeReview(
            @PathVariable Integer reviewId,
            @RequestHeader("Authorization") String token) {

        String confirmToken = token.replace("Bearer ", "");

        if(!jwtTokenProvider.validateToken(confirmToken)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
        }
        // 토큰에서 userId 추출
        Integer userId = jwtTokenProvider.getUserIdFromToken(confirmToken);

        reviewService.likeReview(reviewId, userId);
        return ResponseEntity.ok("리뷰에 좋아요가 성공적으로 등록되었습니다.");
    }

    // 좋아요 여부 확인 (GET)
    @GetMapping("/love/{reviewId}/check")
    public ResponseEntity<Boolean> checkIfUserLikedReview(
            @PathVariable Integer reviewId,
            @RequestHeader("Authorization") String token) {

        String confirmToken = token.replace("Bearer ", "");

        if (!jwtTokenProvider.validateToken(confirmToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        // 토큰에서 userId 추출
        Integer userId = jwtTokenProvider.getUserIdFromToken(confirmToken);

        boolean isLiked = reviewService.isUserLikedReview(reviewId, userId);
        return ResponseEntity.ok(isLiked);
    }

    // 좋아요 개수 조회 (GET)
    @GetMapping("/love/{reviewId}")
    public ResponseEntity<Integer> getLikeCount(@PathVariable Integer reviewId) {
        int likeCount = reviewService.getLikeCount(reviewId);
        return ResponseEntity.ok(likeCount);
    }

    // 좋아요 취소 (DELETE)
    @DeleteMapping("/love/{reviewId}")
    public ResponseEntity<String> unlikeReview(
            @PathVariable Integer reviewId,
            @RequestHeader("Authorization") String token) {

        String confirmToken = token.replace("Bearer ", "");

        if(!jwtTokenProvider.validateToken(confirmToken)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
        }
        // 토큰에서 userId 추출
        Integer userId = jwtTokenProvider.getUserIdFromToken(confirmToken);

        boolean isUnliked = reviewService.unlikeReview(reviewId, userId);
        if (isUnliked) {
            return ResponseEntity.ok("리뷰에 대한 좋아요가 성공적으로 취소되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("좋아요가 등록되지 않았습니다.");
        }
    }

    // 북마크 등록 (POST)
    @PostMapping("/bookmark/{reviewId}")
    public ResponseEntity<String> addBookmark(
            @PathVariable Integer reviewId,
            @RequestHeader("Authorization") String token) {

        String confirmToken = token.replace("Bearer ", "");

        if (!jwtTokenProvider.validateToken(confirmToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
        }

        Integer userId = jwtTokenProvider.getUserIdFromToken(confirmToken);
        reviewService.addBookmark(reviewId, userId);
        return ResponseEntity.ok("리뷰가 북마크에 성공적으로 추가되었습니다.");
    }

    // 북마크 체크 확인 (GET)
    @GetMapping("/bookmark/{reviewId}/check")
    public ResponseEntity<Boolean> checkBookmark(
            @PathVariable Integer reviewId,
            @RequestHeader("Authorization") String token) {

        String confirmToken = token.replace("Bearer ", "");

        if (!jwtTokenProvider.validateToken(confirmToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }

        Integer userId = jwtTokenProvider.getUserIdFromToken(confirmToken);
        boolean isBookmarked = reviewService.isReviewBookmarkedByUser(reviewId, userId);
        return ResponseEntity.ok(isBookmarked);
    }

    // 북마크 취소 (DELETE)
    @DeleteMapping("/bookmark/{reviewId}")
    public ResponseEntity<String> removeBookmark(
            @PathVariable Integer reviewId,
            @RequestHeader("Authorization") String token) {

        String confirmToken = token.replace("Bearer ", "");

        if (!jwtTokenProvider.validateToken(confirmToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
        }

        Integer userId = jwtTokenProvider.getUserIdFromToken(confirmToken);
        boolean isRemoved = reviewService.removeBookmark(reviewId, userId);

        if (isRemoved) {
            return ResponseEntity.ok("북마크가 성공적으로 취소되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("북마크가 존재하지 않습니다.");
        }
    }

//    // 해시태그 추가 (POST)
//    @PostMapping("/hashtag/{reviewId}")
//    public ResponseEntity<String> addHashtagsToReview(
//            @PathVariable Integer reviewId,
//            @RequestBody List<String> hashtags,
//            @RequestHeader("Authorization") String token) {
//
//        String confirmToken = token.replace("Bearer ", "");
//
//        if (!jwtTokenProvider.validateToken(confirmToken)) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
//        }
//
//        Integer userId = jwtTokenProvider.getUserIdFromToken(confirmToken);
//        reviewService.addHashtagsToReview(reviewId, hashtags);
//        return ResponseEntity.ok("해시태그가 리뷰에 성공적으로 추가되었습니다.");
//    }

    // 해시태그 조회 (GET)
    @GetMapping("/hashtag/{reviewId}")
    public ResponseEntity<List<String>> getReviewHashtags(@PathVariable Integer reviewId) {
        List<String> hashtags = reviewService.getReviewHashtags(reviewId);
        if (hashtags.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
        return ResponseEntity.ok(hashtags);
    }

    // 해시태그 삭제 (DELETE)
    @DeleteMapping("/hashtag/{reviewId}")
    public ResponseEntity<String> deleteHashtags(
            @PathVariable Integer reviewId,
            @RequestBody List<String> hashtags,
            @RequestHeader("Authorization") String token) {

        String confirmToken = token.replace("Bearer ", "");

        if (!jwtTokenProvider.validateToken(confirmToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
        }

        Integer userId = jwtTokenProvider.getUserIdFromToken(confirmToken);
        reviewService.deleteHashtags(reviewId, hashtags);
        return ResponseEntity.ok("리뷰에서 해시태그가 성공적으로 삭제되었습니다.");
    }
}
