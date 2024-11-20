package com.trip.enjoy_trip.service;

import com.trip.enjoy_trip.dto.AttractionDto;
import com.trip.enjoy_trip.dto.MarkerDto;

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


    boolean checkAttractionExists(Integer attractionId); //마커 추가하기 전 명소 id 중복체크
    void addMarker(Double latitude, Double longitude, Integer userId, Integer attractionId, Integer gugunId, Integer sidoId); //마커 추가하기
    List<MarkerDto> getUserMarkers(Integer userId); // 사용자의 마커 목록 조회
    boolean deleteMarker(Integer markerId, Integer userId); // 마커 삭제
}
