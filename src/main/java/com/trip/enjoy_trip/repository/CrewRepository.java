package com.trip.enjoy_trip.repository;

import com.trip.enjoy_trip.dto.CrewDto;
import com.trip.enjoy_trip.dto.CrewUserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CrewRepository {
    void createCrew(CrewDto crewDto);
    void addUserToCrew(CrewUserDto crewUserDto);
    CrewDto findCrewById(int crewId);
    List<Integer> findUserIdsByCrewId(int crewId); // Crew에 속한 유저 ID 리스트 조회
    // 크루와 관련된 사용자 삭제
    void deleteCrewUsersByCrewId(@Param("crewId") int crewId);

    // 크루 삭제
    void deleteCrewById(@Param("crewId") int crewId);
}
