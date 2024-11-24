package com.trip.enjoy_trip.service;

import com.trip.enjoy_trip.dto.CrewDto;
import com.trip.enjoy_trip.dto.CrewUserDto;
import com.trip.enjoy_trip.repository.CrewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CrewServiceImpl implements CrewService {

    private final CrewRepository crewRepository;

    @Override
    public int createCrew(CrewDto crewDto, List<Integer> userIds) {
        crewRepository.createCrew(crewDto);

        for (Integer userId : userIds) {
            CrewUserDto crewUserDto = new CrewUserDto();
            crewUserDto.setCrewId(crewDto.getCrewId());
            crewUserDto.setUserId(userId);
            crewRepository.addUserToCrew(crewUserDto);
        }

        return crewDto.getCrewId();
    }
    @Transactional
    @Override
    public void deleteCrew(int crewId) {
        // 삭제 수행
        crewRepository.deleteCrewUsersByCrewId(crewId); // 관련된 모든 사용자 삭제
        crewRepository.deleteCrewById(crewId);// 크루 삭제
        crewRepository.deleteReviewHashtagsByCrewId(crewId);
        crewRepository.deleteReviewImagesByCrewId(crewId);
        crewRepository.deleteReviewByCrewId(crewId);
    }
    @Override
    public CrewDto getCrewById(int crewId) {
        return crewRepository.findCrewById(crewId)
                .orElseThrow(() -> new IllegalArgumentException("Crew not found with ID: " + crewId));
    }

    @Override
    public List<CrewUserDto> getUsersByCrewId(int crewId) {
        return crewRepository.findUsersByCrewId(crewId);
    }
    @Override
    public List<CrewDto> getCrewsByUserId(int userId) {
        return crewRepository.findCrewsByUserId(userId);
    }

    @Override
    public void leaveCrew(int crewId, int userId) {
        crewRepository.leaveCrew(crewId,userId);
    }
    @Override
    public void updateCrew(int crewId, String name, List<Integer> userIds) {
        // 모임 이름 업데이트
        crewRepository.updateCrewName(crewId, name);

        // 기존 유저 삭제
        crewRepository.deleteCrewUsersByCrewId(crewId);

        // 새로운 유저 추가
        for (Integer userId : userIds) {
            CrewUserDto crewUserDto = new CrewUserDto();
            crewUserDto.setCrewId(crewId);
            crewUserDto.setUserId(userId);
            crewRepository.addUserToCrew(crewUserDto);
        }
    }

}
