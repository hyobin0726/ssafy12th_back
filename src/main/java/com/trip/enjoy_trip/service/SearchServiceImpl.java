package com.trip.enjoy_trip.service;

import com.trip.enjoy_trip.dto.ReviewDto;
import com.trip.enjoy_trip.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService{
    private final SearchRepository searchRepository;


    @Override
    public List<ReviewDto> getReviewsByHashtag(String hashtagName) {
        return searchRepository.findByHashtag(hashtagName);
    }

    public List<String> getSuggestedHashtags(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return Collections.emptyList();
        }
        return searchRepository.findHashtagsByKeyword(keyword);
    }
}
