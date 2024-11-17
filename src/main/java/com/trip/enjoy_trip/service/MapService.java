package com.trip.enjoy_trip.service;

import com.trip.enjoy_trip.dto.AttractionDto;

import java.util.List;

public interface MapService {
    List<AttractionDto> searchAttractionsByTitle(String title); //지도 관광지명 검색
}
