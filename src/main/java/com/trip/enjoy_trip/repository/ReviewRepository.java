package com.trip.enjoy_trip.repository;

import com.trip.enjoy_trip.dto.ReviewDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ReviewRepository {
    void insertReview(ReviewDto reviewDto);
    void insertReviewImages(Integer reviewId, List<String> imageUrls);
    ReviewDto findById(Integer reviewId);        // 특정 리뷰 조회
    List<ReviewDto> findAll();                   // 전체 리뷰 목록 조회

    void updateReview(ReviewDto reviewDto);      // 리뷰 수정
    void deleteReviewImages(Integer reviewId);       // 기존 이미지 삭제
    void deleteReview(Integer reviewId);         // 리뷰 삭제

    //좋아요 기능
    void insertLike(@Param("reviewId") Integer reviewId, @Param("userId") Integer userId);
    int selectLikeCount(@Param("reviewId") Integer reviewId);
    int deleteLike(@Param("reviewId") Integer reviewId, @Param("userId") Integer userId);
    int isUserLikedReview(@Param("reviewId") Integer reviewId, @Param("userId") Integer userId);

    //북마크 기능
    void insertBookmark(@Param("reviewId") Integer reviewId, @Param("userId") Integer userId); //북마크 추가
    int checkBookmark(@Param("reviewId") Integer reviewId, @Param("userId") Integer userId); //북마크 체크 확인
    int deleteBookmark(@Param("reviewId") Integer reviewId, @Param("userId") Integer userId); //북마크 취소

}
