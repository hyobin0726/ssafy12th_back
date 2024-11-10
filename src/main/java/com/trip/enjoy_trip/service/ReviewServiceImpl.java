package com.trip.enjoy_trip.service;

import com.trip.enjoy_trip.dto.ReviewDto;
import com.trip.enjoy_trip.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public ReviewDto getReviewById(Integer reviewId) {
        return reviewRepository.findById(reviewId);
    }

    @Override
    public List<ReviewDto> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public void updateReview(ReviewDto reviewDto) {
        if (reviewDto.getImageUrls() == null || reviewDto.getImageUrls().isEmpty()) {
            throw new IllegalArgumentException("이미지 URL이 필요합니다.");
        }

        // 리뷰 내용 업데이트
        reviewRepository.updateReview(reviewDto);

        // 기존 리뷰 이미지 삭제 후 새로운 이미지 리스트 추가
        reviewRepository.deleteReviewImages(reviewDto.getReviewId());
        if (reviewDto.getImageUrls() != null && !reviewDto.getImageUrls().isEmpty()) {
            reviewRepository.insertReviewImages(reviewDto.getReviewId(), reviewDto.getImageUrls());
        }


//        if (reviewDto.getImageUrls() == null || reviewDto.getImageUrls().isEmpty()) {
//            throw new IllegalArgumentException("이미지 URL이 필요합니다.");
//        }
//        // 리뷰 내용 업데이트
//        reviewRepository.updateReview(reviewDto);
//        // 기존 리뷰 이미지 삭제 후 새로운 이미지 리스트 추가
//        reviewRepository.deleteReviewImages(reviewDto.getReviewId());
//
//        // 리뷰 ID를 가져와 리뷰 이미지 추가
//        reviewRepository.insertReviewImages(reviewDto.getReviewId(), reviewDto.getImageUrls());
    }

    @Override
    public void deleteReview(Integer reviewId) {
        // 리뷰 이미지 삭제
        reviewRepository.deleteReviewImages(reviewId);

        // 리뷰 삭제
        reviewRepository.deleteReview(reviewId);
    }

    //리뷰 좋아요 누르기
    @Override
    public void likeReview(Integer reviewId, Integer userId) {
        reviewRepository.insertLike(reviewId, userId);
    }

    //좋아요 조회
    @Override
    public int getLikeCount(Integer reviewId) {
        return reviewRepository.selectLikeCount(reviewId);
    }

    //좋아요 취소
    @Override
    public boolean unlikeReview(Integer reviewId, Integer userId) {
        int deletedRows = reviewRepository.deleteLike(reviewId, userId);
        return deletedRows > 0;
    }
    //사용자의 좋아요 여부 조회
    @Override
    public boolean isUserLikedReview(Integer reviewId, Integer userId) {
        return reviewRepository.isUserLikedReview(reviewId, userId) > 0;
    }
}
