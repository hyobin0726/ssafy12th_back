package com.trip.enjoy_trip.service;

import com.trip.enjoy_trip.dto.CrewDto;
import com.trip.enjoy_trip.dto.CrewUserDto;

import java.util.List;

public interface CrewService {
    int createCrew(CrewDto crewDto, List<Integer> userIds); // 유저 리스트 추가
    void deleteCrew(int crewId);
    CrewDto getCrewById(int crewId); // 모임 정보 조회
    List<CrewUserDto> getUsersByCrewId(int crewId); // 모임에 속한 유저 정보 리스트 조회
    List<CrewDto> getCrewsByUserId(int userId);
    void leaveCrew(int crewId, int userId);
    void updateCrew(int crewId, String name, List<Integer> userIds);
}
