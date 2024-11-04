package com.trip.enjoy_trip.repository;

import com.trip.enjoy_trip.dto.ReviewDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ReviewRepository {
    void insertReview(ReviewDto reviewDto);
    void insertReviewImages(int reviewId, List<String> imageUrls);
}
