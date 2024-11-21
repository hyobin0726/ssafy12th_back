package com.trip.enjoy_trip.repository;

import com.trip.enjoy_trip.dto.AttractionDto;
import org.springframework.stereotype.Repository;

@Repository
public interface AttractionRepository {
    AttractionDto findAttractionById(Integer attractionId); //명소id로 명소 정보 조회
}
