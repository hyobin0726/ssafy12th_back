package com.trip.enjoy_trip.repository;

import com.trip.enjoy_trip.dto.AttractionDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MapRepository {

    // Map 검색 API: 지역, 시군구, 관광지명 우선순위로 검색
    List<AttractionDto> findAreasByTitle(String title);     // 지역 검색: 부분 일치로 지역 검색
    List<AttractionDto> findSigungusByTitle(String title);     // 시군구 검색: 부분 일치로 시군구 검색
    List<AttractionDto> findAttractionsByTitle(String title); // 관광지명 검색: title로 관광지명 검색

    List<AttractionDto> findAttractionsByRegion(
            @Param("areaCode") Integer areaCode,
            @Param("siGunGuCode") Integer siGunGuCode); //시군구 검색
    List<AttractionDto> findAttractionsByContentTypeId(@Param("contentTypeId") Integer contentTypeId); //콘텐츠 검색

    List<AttractionDto> findNearbyAttractions(double latitude, double longitude, double radius); //근처 명소 검색
}
