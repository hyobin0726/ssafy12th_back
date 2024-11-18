package com.trip.enjoy_trip.controller;

import com.trip.enjoy_trip.dto.CrewDto;
import com.trip.enjoy_trip.service.CrewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/crew")
@RequiredArgsConstructor
public class CrewController {

    private final CrewService crewService;

    @PostMapping("/create")
    public ResponseEntity<String> createCrew(@RequestBody CreateCrewRequest request) {
        CrewDto crewDto = new CrewDto();
        crewDto.setName(request.getName());
        crewDto.setIntroduction(request.getIntroduction());
        crewDto.setProfileUrl(request.getProfileUrl());

        // 모임 생성 및 유저 추가
        int crewId = crewService.createCrew(crewDto, request.getUserIds());
        return ResponseEntity.ok("Crew created successfully with ID: " + crewId);
    }

    @GetMapping("/{crewId}")
    public ResponseEntity<CrewDto> getCrewById(@PathVariable int crewId) {
        CrewDto crew = crewService.getCrewById(crewId);
        return ResponseEntity.ok(crew);
    }

    @GetMapping("/{crewId}/users")
    public ResponseEntity<List<Integer>> getUsersByCrewId(@PathVariable int crewId) {
        List<Integer> users = crewService.getUsersByCrewId(crewId);
        return ResponseEntity.ok(users);
    }
    @DeleteMapping("/{crewId}")
    public ResponseEntity<String> deleteCrew(@PathVariable int crewId) {
        crewService.deleteCrew(crewId);
        return ResponseEntity.ok("Crew deleted successfully.");
    }


    // 요청 DTO 클래스
    public static class CreateCrewRequest {
        private String name;
        private String introduction;
        private String profileUrl;
        private List<Integer> userIds; // 유저 ID 리스트

        // Getters and Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getProfileUrl() {
            return profileUrl;
        }

        public void setProfileUrl(String profileUrl) {
            this.profileUrl = profileUrl;
        }

        public List<Integer> getUserIds() {
            return userIds;
        }

        public void setUserIds(List<Integer> userIds) {
            this.userIds = userIds;
        }
    }
}
