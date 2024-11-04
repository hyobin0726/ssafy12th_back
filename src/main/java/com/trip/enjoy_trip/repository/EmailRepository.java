package com.trip.enjoy_trip.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EmailRepository {

    // 이메일과 인증 코드를 저장
    void insertEmailVerification(@Param("email") String email, @Param("code") String code);

    // 이메일로 인증 코드 조회 (30분 내 유효)
    String findCodeByEmail(@Param("email") String email);

    // 인증 완료된 코드 삭제
    void deleteCodeByEmail(@Param("email") String email);
}
