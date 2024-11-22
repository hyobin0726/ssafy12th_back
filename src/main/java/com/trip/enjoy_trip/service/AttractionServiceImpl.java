package com.trip.enjoy_trip.service;

import com.trip.enjoy_trip.dto.AttractionDto;
import com.trip.enjoy_trip.repository.AttractionRepository;
import org.springframework.stereotype.Service;

@Service
public class AttractionServiceImpl implements AttractionService {

    private final AttractionRepository attractionRepository;

    public AttractionServiceImpl(AttractionRepository attractionRepository) {
        this.attractionRepository = attractionRepository;
    }

    //명소id로 명소 정보 조회
    @Override
    public AttractionDto getAttractionById(Integer attractionId) {
        return attractionRepository.findAttractionById(attractionId);
    }
}
