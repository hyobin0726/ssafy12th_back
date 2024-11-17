package com.trip.enjoy_trip.repository;

import com.trip.enjoy_trip.dto.AttractionDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MapRepository {
    List<AttractionDto> findAttractionsByTitle(String title); //관광지명 검색
    List<AttractionDto> findAttractionsByRegion(
            @Param("areaCode") Integer areaCode,
            @Param("siGunGuCode") Integer siGunGuCode); //시군구 검색
    List<AttractionDto> findAttractionsByContentTypeId(@Param("contentTypeId") Integer contentTypeId); //콘텐츠 검색
}
