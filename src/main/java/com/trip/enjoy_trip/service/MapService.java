package com.trip.enjoy_trip.service;

import com.trip.enjoy_trip.dto.AttractionDto;

import java.util.List;

public interface MapService {
    // Map 검색 API지역, 시군구, 관광지명 검색
    // 지역 검색
    List<AttractionDto> searchAreasByTitle(String title);
    // 시군구 검색
    List<AttractionDto> searchSigungusByTitle(String title);
    // 관광지명 검색
    List<AttractionDto> searchAttractionsByTitle(String title);

    List<AttractionDto> getAttractionsByRegion(Integer areaCode, Integer siGunGuCode); //시군구 검색
    List<AttractionDto> searchAttractionsByContentTypeId(Integer contentTypeId); //콘텐츠 검색

    List<AttractionDto> searchNearbyAttractions(double latitude, double longitude, double radius); //근처 명소 검색


    void addMarker(Double latitude, Double longitude, Integer userId, Integer attractionId, Integer gugunId, Integer sidoId); //마커 추가하기
}
