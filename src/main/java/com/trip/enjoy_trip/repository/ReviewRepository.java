package com.trip.enjoy_trip.repository;

import com.trip.enjoy_trip.dto.ReviewDto;
import com.trip.enjoy_trip.dto.CommentDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ReviewRepository {
    //리뷰 작성
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

    //해시태그 기능
    //해시태그 등록
    // 해시태그 ID 조회
    Integer findHashtagId(String hashtag);

    // 해시태그 새로 생성
    void createHashtag(String hashtag);

    // 리뷰와 해시태그 연결
    void addHashtagToReview(int reviewId, int hashtagId);

    List<String> findHashtagsByReviewId(Integer reviewId);  // 특정 리뷰에 등록된 해시태그 조회
    Integer findHashtagIdByName(String hashtag);  // 해시태그 이름으로 ID 찾기(삭제 용도)
    void deleteHashtagFromReview(@Param("reviewId") Integer reviewId, @Param("hashtagId") Integer hashtagId);  // 특정 리뷰에서 해시태그 삭제

    void deleteHashtagsByReviewId(Integer reviewId);    // 리뷰의 기존 해시태그 삭제

    //댓글 기능
    void insertComment(CommentDto commentDto); //댓글 작성
    List<CommentDto> findCommentsByReviewId(Integer reviewId);  // 리뷰에 달린 댓글 조회
    void updateComment(CommentDto commentDto);        // 댓글 수정
    Integer findCommentOwner(Integer commentId);      // 댓글 작성자 확인
    void deleteComment(Integer commentId);        // 댓글 삭제 메서드
    int findCommentCountByReviewId(Integer reviewId);  // 댓글 수 조회 메서드 선언

    //마이페이지 작성한 리뷰 조회 & 북마크한 리뷰 조회
    List<ReviewDto> findReviewsByUserId(Integer userId);  // 사용자가 작성한 리뷰 조회
    List<ReviewDto> findBookmarkedReviewsByUserId(Integer userId);  // 사용자가 북마크한 리뷰 조회

    //해당 크루 리뷰 조회
    List<ReviewDto> findReviewsByCrewId(int crewId);

    //명소 제목을 기반으로 리뷰 조회
    List<ReviewDto> findReviewsByAttractionId(Integer attractionId); //명소 제목을 기반으로 리뷰 조회
    Double findAveragePointByTitle(String title);  //리뷰의 평균 별점 조회

}
