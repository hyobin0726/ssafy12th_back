package com.trip.enjoy_trip.controller;

import com.trip.enjoy_trip.dto.AttractionDto;
import com.trip.enjoy_trip.dto.MarkerDto;
import com.trip.enjoy_trip.security.JwtTokenProvider;
import com.trip.enjoy_trip.service.JwtTokenService;
import com.trip.enjoy_trip.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/map")
public class MapController {

    @Autowired
    private final MapService mapService;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenService jwtTokenService;

    @Autowired
    public MapController(MapService mapService, JwtTokenProvider jwtTokenProvider, JwtTokenService jwtTokenService) {
        this.mapService = mapService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtTokenService = jwtTokenService;
    }

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

    //마커 기능
    @PostMapping("/marker")
    public ResponseEntity<String> addMarker(
            @RequestHeader("Authorization") String token,
            @RequestBody MarkerDto markerDto) {

        String confirmToken = token.replace("Bearer ", "");

        if (!jwtTokenProvider.validateToken(confirmToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
        }

        Integer userId = jwtTokenProvider.getUserIdFromToken(confirmToken);

        boolean isAttractionExists = mapService.checkAttractionExists(markerDto.getAttractionId());
        if (isAttractionExists) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 해당 attraction에 마커가 존재합니다.");
        }
        mapService.addMarker(markerDto.getLatitude(), markerDto.getLongitude(), userId,
                markerDto.getAttractionId(), markerDto.getGugunId(), markerDto.getSidoId());
        return ResponseEntity.ok("마커가 성공적으로 추가되었습니다.");
    }

    // 사용자의 마커 목록 조회
    @GetMapping("/marker")
    public ResponseEntity<List<MarkerDto>> getUserMarkers(@RequestHeader("Authorization") String token) {
        String confirmToken = token.replace("Bearer ", "");

        if (!jwtTokenProvider.validateToken(confirmToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Integer userId = jwtTokenProvider.getUserIdFromToken(confirmToken);
        List<MarkerDto> markers = mapService.getUserMarkers(userId);
        return ResponseEntity.ok(markers);
    }

    // 마커 삭제
    @DeleteMapping("/marker/{markerId}")
    public ResponseEntity<String> deleteMarker(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer markerId) {

        String confirmToken = token.replace("Bearer ", "");

        if (!jwtTokenProvider.validateToken(confirmToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
        }

        Integer userId = jwtTokenProvider.getUserIdFromToken(confirmToken);
        boolean isDeleted = mapService.deleteMarker(markerId, userId);

        if (isDeleted) {
            return ResponseEntity.ok("마커가 성공적으로 삭제되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("삭제 권한이 없습니다.");
        }
    }
}
