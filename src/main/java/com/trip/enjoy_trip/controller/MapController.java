package com.trip.enjoy_trip.controller;

import com.trip.enjoy_trip.dto.AttractionDto;
import com.trip.enjoy_trip.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/map")
public class MapController {

    @Autowired
    private MapService mapService;

    @GetMapping("/search")
    public List<AttractionDto> searchAttractions(@RequestParam("title") String title) {
        return mapService.searchAttractionsByTitle(title);
    }
}
