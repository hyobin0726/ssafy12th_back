package com.trip.enjoy_trip.controller;

import com.trip.enjoy_trip.dto.AttractionDto;
import com.trip.enjoy_trip.service.AttractionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/attractions")
public class AttractionController {

    private final AttractionService attractionService;

    public AttractionController(AttractionService attractionService) {
        this.attractionService = attractionService;
    }

    //명소id로 명소 정보 조회
    @GetMapping("/{attractionId}")
    public ResponseEntity<AttractionDto> getAttractionById(@PathVariable Integer attractionId) {
        AttractionDto attraction = attractionService.getAttractionById(attractionId);
        if (attraction == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(attraction);
    }
}
