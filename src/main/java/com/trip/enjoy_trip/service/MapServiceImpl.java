package com.trip.enjoy_trip.service.impl;

import com.trip.enjoy_trip.dto.AttractionDto;
import com.trip.enjoy_trip.dto.MarkerDto;
import com.trip.enjoy_trip.repository.MapRepository;
import com.trip.enjoy_trip.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapServiceImpl implements MapService {

    @Autowired
    private MapRepository mapRepository;


    //Map 검색 API: 지역, 시군구, 관광지명 순서로 검색
    @Override
    public List<AttractionDto> searchAreasByTitle(String title) {
        return mapRepository.findAreasByTitle(title);
    }
    @Override
    public List<AttractionDto> searchSigungusByTitle(String title) {
        return mapRepository.findSigungusByTitle(title);
    }
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

    //근처 명소 검색
    @Override
    public List<AttractionDto> searchNearbyAttractions(double latitude, double longitude, double radius) {
        return mapRepository.findNearbyAttractions(latitude, longitude, radius);
    }


    //마커 기능
    //마커 추가하기
    @Override
    public void addMarker(Double latitude, Double longitude, Integer userId, Integer attractionId, Integer gugunId, Integer sidoId) {
        if (mapRepository.isMarkerExists(userId, attractionId)) { //중복체크
            throw new IllegalArgumentException("이미 해당 명소에 마커가 존재합니다.");
        }
        mapRepository.addMarker(latitude, longitude, userId, attractionId, gugunId, sidoId);
    }

    // 사용자의 마커 목록 조회
    @Override
    public List<MarkerDto> getUserMarkers(Integer userId) {
        return mapRepository.findMarkersByUserId(userId);
    }

    // 마커 삭제
    @Override
    public boolean deleteMarker(Integer markerId, Integer userId) {
        // 먼저 사용자가 해당 마커의 소유자인지 확인
        Integer ownerId = mapRepository.findMarkerOwner(markerId);
        if (ownerId != null && ownerId.equals(userId)) {
            mapRepository.deleteMarker(markerId);
            return true;
        }
        return false; // 소유자가 아닌 경우 삭제 불가
    }
}
