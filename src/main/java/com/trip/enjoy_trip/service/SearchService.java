package com.trip.enjoy_trip.service;

import com.trip.enjoy_trip.dto.ReviewDto;

import java.util.List;

public interface SearchService {
    List<ReviewDto> getReviewsByHashtag(String hashtagName);
    List<String> getSuggestedHashtags(String keyword);
}
