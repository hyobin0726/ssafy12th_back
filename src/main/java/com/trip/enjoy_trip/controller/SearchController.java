package com.trip.enjoy_trip.controller;


import com.trip.enjoy_trip.dto.ReviewDto;
import com.trip.enjoy_trip.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @GetMapping("/search")
    public ResponseEntity<List<ReviewDto>> getReviewsByHashtag(@RequestParam String hashtagName) {
        List<ReviewDto> reviews = searchService.getReviewsByHashtag(hashtagName);
        return ResponseEntity.ok(reviews);
    }
    @GetMapping("/hashSearch")
    public ResponseEntity<List<String>> getHashtagSuggestions(@RequestParam String keyword) {
        List<String> suggestions = searchService.getSuggestedHashtags(keyword);
        return ResponseEntity.ok(suggestions);
    }

}
