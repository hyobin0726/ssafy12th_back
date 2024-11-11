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

    //리뷰 작성
    @Override
    public void createReview(ReviewDto reviewDto) {
        // imageUrls가 비어 있을 경우 예외 처리
        if (reviewDto.getImageUrls() == null || reviewDto.getImageUrls().isEmpty()) {
            throw new IllegalArgumentException("이미지 URL이 필요합니다.");
        }

        // 리뷰 저장
        reviewRepository.insertReview(reviewDto);
        // 리뷰 ID를 가져와 리뷰 이미지 추가
        Integer reviewId = reviewDto.getReviewId();
        reviewRepository.insertReviewImages(reviewId, reviewDto.getImageUrls());

        // 해시태그 처리
        List<String> hashtags = reviewDto.getHashtags();
        if (hashtags != null && !hashtags.isEmpty()) {
            for (String hashtag : hashtags) {
                // 해시태그 ID 찾기
                Integer hashtagId = reviewRepository.findHashtagId(hashtag);
                if (hashtagId == null) {
                    // 해시태그가 없으면 새로 생성
                    reviewRepository.createHashtag(hashtag);
                    hashtagId = reviewRepository.findHashtagId(hashtag);
                }
                // 리뷰와 해시태그 연결
                reviewRepository.addHashtagToReview(reviewId, hashtagId);
            }
        }
    }

    @Override
    public ReviewDto getReviewById(Integer reviewId) {
        return reviewRepository.findById(reviewId);
    }

    //리뷰 조회
    @Override
    public List<ReviewDto> getAllReviews() {
        return reviewRepository.findAll();
    }

    //리뷰 수정
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
    }

    //리뷰 삭제
    @Override
    public void deleteReview(Integer reviewId) {
        // 리뷰 이미지 삭제
        reviewRepository.deleteReviewImages(reviewId);

        // 리뷰 삭제
        reviewRepository.deleteReview(reviewId);
    }

    // 좋아요 기능 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
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

    //북마크 기능 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
    //북마크 추가
    @Override
    public void addBookmark(Integer reviewId, Integer userId) {
        reviewRepository.insertBookmark(reviewId, userId);
    }

    //북마크 체크 확인
    @Override
    public boolean isReviewBookmarkedByUser(Integer reviewId, Integer userId) {
        return reviewRepository.checkBookmark(reviewId, userId) > 0;
    }

    //북마크 취소
    @Override
    public boolean removeBookmark(Integer reviewId, Integer userId) {
        return reviewRepository.deleteBookmark(reviewId, userId) > 0;
    }

    //해시태그 기능 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
    //해시태그 추가
//    @Override
//    public void addHashtagsToReview(Integer reviewId, List<String> hashtags) {
//        for (String hashtag : hashtags) {
//            // 해시태그 ID 찾기 또는 없으면 생성 후 가져오기
//            Integer hashtagId = reviewRepository.findHashtagId(hashtag);
//            if (hashtagId == null) {
//                reviewRepository.createHashtag(hashtag);
//                hashtagId = reviewRepository.findHashtagId(hashtag);
//            }
//            // 리뷰에 해시태그 추가
//            reviewRepository.addHashtagToReview(reviewId, hashtagId);
//        }
//    }

    //해시태그 조회
    @Override
    public List<String> getHashtagsByReviewId(Integer reviewId) {
        return reviewRepository.findHashtagsByReviewId(reviewId);
    }

    //해시태그 삭제
    @Override
    public void deleteHashtags(Integer reviewId, List<String> hashtags) {
        for (String hashtag : hashtags) {
            Integer hashtagId = reviewRepository.findHashtagIdByName(hashtag);
            if (hashtagId != null) {
                reviewRepository.deleteHashtagFromReview(reviewId, hashtagId);
            }
        }
    }
}
