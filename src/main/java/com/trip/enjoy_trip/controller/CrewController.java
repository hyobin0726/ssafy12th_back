package com.trip.enjoy_trip.controller;

import com.trip.enjoy_trip.dto.CrewDto;
import com.trip.enjoy_trip.dto.CrewUserDto;
import com.trip.enjoy_trip.security.JwtTokenProvider;
import com.trip.enjoy_trip.service.CrewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/crew")
@RequiredArgsConstructor
public class CrewController {

    private final CrewService crewService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/create")
    public ResponseEntity<String> createCrew(@RequestHeader("Authorization") String token, @RequestBody CrewDto crewDto) {
        // Bearer 토큰에서 실제 토큰 값 추출
        String authToken = token.replace("Bearer ", "");
        Integer userId = jwtTokenProvider.getUserIdFromToken(authToken);
        List<Integer> userIds = new ArrayList<>(
                crewDto.getUsers().stream()
                        .map(CrewUserDto::getUserId)
                        .toList()
        );

        // 자기 자신(userId) 추가
        if (!userIds.contains(userId)) {
            userIds.add(userId);
        }

        int crewId = crewService.createCrew(crewDto, userIds);
        return ResponseEntity.ok("모임 생성 완료 " + crewId);
    }

    //모임 삭제
    @DeleteMapping("/{crewId}")
    public ResponseEntity<String> deleteCrew(@PathVariable int crewId) {
        crewService.deleteCrew(crewId);
        return ResponseEntity.ok("모임 삭제 성공");
    }

    @GetMapping("/{crewId}/details")
    public ResponseEntity<CrewDto> getCrewDetails(@PathVariable int crewId) {
        CrewDto crew = crewService.getCrewById(crewId); // 모임 정보 조회
        List<CrewUserDto> users = crewService.getUsersByCrewId(crewId); // 유저 정보 조회

        // 모임 정보에 유저 리스트 포함 (필요 시 CrewDto에 유저 리스트 추가)
        crew.setUsers(users);

        return ResponseEntity.ok(crew);
    }
    //사용자 모임 조회
    @GetMapping("/myCrew")
    public ResponseEntity<List<CrewDto>> getMyCrews(@RequestHeader("Authorization") String token) {

        String authToken = token.replace("Bearer ", "");
        // JWT에서 userId 추출
        Integer userId = jwtTokenProvider.getUserIdFromToken(authToken);
        // userId로 모임 조회
        List<CrewDto> crews = crewService.getCrewsByUserId(userId);

        return ResponseEntity.ok(crews);
    }
    //모임 떠나기
    @DeleteMapping("/{crewId}/leave")
    public ResponseEntity<String> leaveCrew(
            @RequestHeader("Authorization") String token,
            @PathVariable int crewId) {
        String authToken = token.replace("Bearer ", "");
        Integer userId = jwtTokenProvider.getUserIdFromToken(authToken);

        // 모임 나가기 서비스 호출
        crewService.leaveCrew(crewId, userId);

        return ResponseEntity.ok("모임 나가기 성공");
    }

    @PutMapping("/{crewId}")
    public ResponseEntity<String> updateCrew(@PathVariable int crewId, @RequestBody CrewDto crewDto, @RequestHeader("Authorization") String token) {
        String authToken = token.replace("Bearer ", "");
        Integer userId = jwtTokenProvider.getUserIdFromToken(authToken);

        List<Integer> userIds = new ArrayList<>(
                crewDto.getUsers().stream()
                        .map(CrewUserDto::getUserId)
                        .toList()
        );

        if (!userIds.contains(userId)) {
            userIds.add(userId); // 자신 추가
        }

        crewService.updateCrew(crewId, crewDto.getName(), userIds);
        return ResponseEntity.ok("모임 수정 완료");
    }


}
