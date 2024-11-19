package com.trip.enjoy_trip.controller;

import com.trip.enjoy_trip.dto.AttractionDto;
import com.trip.enjoy_trip.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/map")
public class MapController {

    @Autowired
    private MapService mapService;

    // 검색 API: 지역, 시군구, 관광지명 우선순위로 검색
    @GetMapping("/search/title")
    public Map<String, Object> searchTitle(@RequestParam("title") String title) {
        Map<String, Object> response = new HashMap<>();

        // 지역 검색
        List<AttractionDto> areas = mapService.searchAreasByTitle(title);
        response.put("area", areas.isEmpty() ? null : areas);

        // 시군구 검색
        List<AttractionDto> sigungus = mapService.searchSigungusByTitle(title);
        response.put("sigungu", sigungus.isEmpty() ? null : sigungus);

        // 관광지명 검색
        List<AttractionDto> attractions = mapService.searchAttractionsByTitle(title);
        response.put("attractions", attractions.isEmpty() ? null : attractions);

        return response;
    }

    //시군구 검색
    @GetMapping("/search/region")
    public List<AttractionDto> getAttractionsByRegion(
            @RequestParam(value = "areaCode", required = false) Integer areaCode,
            @RequestParam(value = "siGunGuCode", required = false) Integer siGunGuCode) {
        return mapService.getAttractionsByRegion(areaCode, siGunGuCode);
    }

    //콘텐츠 검색
    @GetMapping("/search/content")
    public List<AttractionDto> getAttractionsByContentTypeId(@RequestParam Integer contentTypeId) {
        return mapService.searchAttractionsByContentTypeId(contentTypeId);
    }

    //근처 명소 검색
    @GetMapping("/search/nearby")
    public List<AttractionDto> searchNearbyAttractions(@RequestParam("latitude") double latitude,
                                                       @RequestParam("longitude") double longitude,
                                                       @RequestParam("radius") double radius) {
        return mapService.searchNearbyAttractions(latitude, longitude, radius);
    }

}
