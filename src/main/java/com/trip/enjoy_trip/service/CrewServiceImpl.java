package com.trip.enjoy_trip.service;

import com.trip.enjoy_trip.dto.CrewDto;
import com.trip.enjoy_trip.dto.CrewUserDto;
import com.trip.enjoy_trip.repository.CrewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CrewServiceImpl implements CrewService {

    private final CrewRepository crewRepository;

    @Override
    public int createCrew(CrewDto crewDto, List<Integer> userIds) {
        // 모임(Crew) 생성
        crewRepository.createCrew(crewDto);

        // Crew_User 추가 (모든 사용자 추가)
        for (Integer userId : userIds) {
            CrewUserDto crewUserDto = new CrewUserDto();
            crewUserDto.setCrewId(crewDto.getCrewId());
            crewUserDto.setUserId(userId);
            crewRepository.addUserToCrew(crewUserDto);
        }

        return crewDto.getCrewId();
    }

    @Override
    public CrewDto getCrewById(int crewId) {
        return crewRepository.findCrewById(crewId);
    }

    @Override
    public List<Integer> getUsersByCrewId(int crewId) {
        return crewRepository.findUserIdsByCrewId(crewId);
    }
    @Override
    public void deleteCrew(int crewId) {
        // 먼저 존재 여부를 확인
        CrewDto crew = crewRepository.findCrewById(crewId);

        // 삭제 수행
        crewRepository.deleteCrewUsersByCrewId(crewId); // 관련된 모든 사용자 삭제
        crewRepository.deleteCrewById(crewId);         // 크루 삭제
    }

}
