package com.trip.enjoy_trip.repository;

import com.trip.enjoy_trip.dto.CrewDto;
import com.trip.enjoy_trip.dto.CrewUserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CrewRepository {
    //모임 생성
    void createCrew(CrewDto crewDto);
    void addUserToCrew(CrewUserDto crewUserDto);
    // 크루와 관련된 사용자 삭제
    void deleteCrewUsersByCrewId(@Param("crewId") int crewId);
    // 크루 삭제
    void deleteCrewById(@Param("crewId") int crewId);

    Optional<CrewDto> findCrewById(@Param("crewId") int crewId);
    List<CrewUserDto> findUsersByCrewId(@Param("crewId") int crewId);

    //사용자 모임 조회
    List<CrewDto> findCrewsByUserId(@Param("userId") int userId);
    //모임 나가기
    int leaveCrew(@Param("crewId") int crewId, @Param("userId") int  userid);
    //모임 수정
    void updateCrewName(int crewId, String name);

}
