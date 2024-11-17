package com.trip.enjoy_trip.controller;

import com.trip.enjoy_trip.dto.AttractionDto;
import com.trip.enjoy_trip.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/map")
public class MapController {

    @Autowired
    private MapService mapService;

    @GetMapping("/search/title")
    public List<AttractionDto> searchAttractions(@RequestParam("title") String title) {
        return mapService.searchAttractionsByTitle(title);
    }

    @GetMapping("/search/region")
    public List<AttractionDto> getAttractionsByRegion(
            @RequestParam(value = "areaCode", required = false) Integer areaCode,
            @RequestParam(value = "siGunGuCode", required = false) Integer siGunGuCode) {
        return mapService.getAttractionsByRegion(areaCode, siGunGuCode);
    }
}
