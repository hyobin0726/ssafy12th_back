package com.trip.enjoy_trip.repository;

import com.trip.enjoy_trip.dto.AttractionDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MapRepository {
    List<AttractionDto> findAttractionsByTitle(String title); //지도 관광지명 검색
}
