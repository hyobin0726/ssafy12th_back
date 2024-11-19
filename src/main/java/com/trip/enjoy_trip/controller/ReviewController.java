package com.trip.enjoy_trip.controller;

import com.trip.enjoy_trip.dto.CommentDto;
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

//        System.out.println("Received ReviewDto in Controller: {} " +  reviewDto.getAttractionId() + " - " + reviewDto.getGugunId() + " - " + reviewDto.getGugunSidoId());

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

    // 해시태그 조회 (GET)
    @GetMapping("/hashtag/{reviewId}")
    public ResponseEntity<List<String>> getReviewHashtags(@PathVariable Integer reviewId) {
        List<String> hashtags = reviewService.getReviewHashtags(reviewId);
//        if (hashtags.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
//        }
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


    // 댓글 작성
    @PostMapping("/comments/{reviewId}")
    public ResponseEntity<String> createComment(
            @PathVariable Integer reviewId,
            @Valid @RequestBody CommentDto commentDto,
            @RequestHeader("Authorization") String token) {

        String confirmToken = token.replace("Bearer ", "");

        if (!jwtTokenProvider.validateToken(confirmToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
        }

        // 토큰에서 userId 추출
        Integer userId = jwtTokenProvider.getUserIdFromToken(confirmToken);

        // 댓글 DTO에 userId 및 reviewId 설정
        commentDto.setUserId(userId);
        commentDto.setReviewId(reviewId);

        reviewService.createComment(commentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("댓글이 성공적으로 작성되었습니다.");
    }

    // 리뷰에 달린 댓글 조회
    @GetMapping("/comments/{reviewId}")
    public ResponseEntity<List<CommentDto>> getCommentsByReview(@PathVariable Integer reviewId) {
        List<CommentDto> comments = reviewService.getCommentsByReview(reviewId);
        return ResponseEntity.ok(comments);
    }

    // 댓글 수정
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<String> updateComment(
            @PathVariable Integer commentId,
            @Valid @RequestBody CommentDto commentDto,
            @RequestHeader("Authorization") String token) {

        String confirmToken = token.replace("Bearer ", "");

        if (!jwtTokenProvider.validateToken(confirmToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
        }

        // 토큰에서 userId 추출
        Integer userId = jwtTokenProvider.getUserIdFromToken(confirmToken);

        // 댓글 수정 요청 시 userId와 댓글의 소유자 ID를 비교하여 권한 확인
        if (!reviewService.isCommentOwner(commentId, userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("댓글을 수정할 수 없습니다.");
        }

        // commentId와 새로운 content를 설정하여 서비스 호출
        commentDto.setCommentId(commentId);
        commentDto.setUserId(userId);
        reviewService.updateComment(commentDto);

        return ResponseEntity.ok("댓글이 성공적으로 수정되었습니다.");
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable Integer commentId,
            @RequestHeader("Authorization") String token) {

        String confirmToken = token.replace("Bearer ", "");

        if (!jwtTokenProvider.validateToken(confirmToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
        }

        // 토큰에서 userId 추출
        Integer userId = jwtTokenProvider.getUserIdFromToken(confirmToken);

        // 댓글 소유자 확인
        if (!reviewService.isCommentOwner(commentId, userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("댓글을 삭제할 수 없습니다.");
        }

        // 댓글 삭제 처리
        reviewService.deleteComment(commentId);
        return ResponseEntity.ok("댓글이 성공적으로 삭제되었습니다.");
    }

    // 특정 리뷰에 달린 댓글 수 조회
    @GetMapping("/comments/{reviewId}/count")
    public ResponseEntity<Integer> getCommentCountByReviewId(@PathVariable Integer reviewId) {
        int commentCount = reviewService.getCommentCountByReviewId(reviewId);
        return ResponseEntity.ok(commentCount);
    }

    //마이페이지에 내가 작성한 리뷰 조회 & 내가 북마크한 리뷰 조회
    // 사용자가 작성한 리뷰 조회
    @GetMapping("/my-reviews")
    public ResponseEntity<List<ReviewDto>> getMyReviews(@RequestHeader("Authorization") String token) {
        String confirmToken = token.replace("Bearer ", "");
        Integer userId = jwtTokenProvider.getUserIdFromToken(confirmToken);
        List<ReviewDto> myReviews = reviewService.getMyReviews(userId);
        return ResponseEntity.ok(myReviews);
    }

    // 사용자가 북마크한 리뷰 조회
    @GetMapping("/my-bookmarks")
    public ResponseEntity<List<ReviewDto>> getBookmarkedReviews(@RequestHeader("Authorization") String token) {
        String confirmToken = token.replace("Bearer ", "");
        Integer userId = jwtTokenProvider.getUserIdFromToken(confirmToken);
        List<ReviewDto> bookmarkedReviews = reviewService.getBookmarkedReviews(userId);
        return ResponseEntity.ok(bookmarkedReviews);
    }

    //명소 제목을 기반으로 리뷰 조회
    @GetMapping("/search-attraction")
    public ResponseEntity<List<ReviewDto>> getReviewsByTitle(@RequestParam String title) {
        List<ReviewDto> reviews = reviewService.getReviewsByTitle(title);

        return ResponseEntity.ok(reviews);
    }
}
