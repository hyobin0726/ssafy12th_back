package com.trip.enjoy_trip.repository;

import com.trip.enjoy_trip.dto.ReviewDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SearchRepository {
    List<ReviewDto> findByHashtag(@Param("hashtagName") String hashtagName);
    List<String> findHashtagsByKeyword(@Param("keyword") String keyword);
}
