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


    //지도 관광지명 검색
    @Override
    public List<AttractionDto> searchAttractionsByTitle(String title) {
        return mapRepository.findAttractionsByTitle(title);
    }
}
