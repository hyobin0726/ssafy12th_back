package com.trip.enjoy_trip.service;

import com.trip.enjoy_trip.dto.AttractionDto;

public interface AttractionService {
    AttractionDto getAttractionById(Integer attractionId); //명소id로 명소 정보 조회
}

