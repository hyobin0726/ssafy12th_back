package com.trip.enjoy_trip.service.impl;

import com.trip.enjoy_trip.dto.AttractionDto;
import com.trip.enjoy_trip.repository.MapRepository;
import com.trip.enjoy_trip.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapServiceImpl implements MapService {

    @Autowired
    private MapRepository mapRepository;


    //관광지명 검색
    @Override
    public List<AttractionDto> searchAttractionsByTitle(String title) {
        return mapRepository.findAttractionsByTitle(title);
    }
    //시군구 검색
    @Override
    public List<AttractionDto> getAttractionsByRegion(Integer areaCode, Integer siGunGuCode) {
        return mapRepository.findAttractionsByRegion(areaCode, siGunGuCode);
    }
    //콘텐츠 검색
    @Override
    public List<AttractionDto> searchAttractionsByContentTypeId(Integer contentTypeId) {
        return mapRepository.findAttractionsByContentTypeId(contentTypeId);
    }
}
