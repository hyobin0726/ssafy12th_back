package com.trip.enjoy_trip.service;

import com.trip.enjoy_trip.dto.CrewDto;

import java.util.List;

public interface CrewService {
    int createCrew(CrewDto crewDto, List<Integer> userIds); // 유저 리스트 추가
    CrewDto getCrewById(int crewId);
    List<Integer> getUsersByCrewId(int crewId); // 모임에 포함된 유저 ID 리스트 반환
    public void deleteCrew(int crewId);

}
