package com.trip.enjoy_trip.service;

import com.trip.enjoy_trip.dto.AttractionDto;

import java.util.List;

public interface MapService {
    List<AttractionDto> searchAttractionsByTitle(String title); //관광지명 검색
    List<AttractionDto> getAttractionsByRegion(Integer areaCode, Integer siGunGuCode); //시군구 검색
}
