package com.trip.enjoy_trip.repository;

import com.trip.enjoy_trip.dto.LoginDto;
import com.trip.enjoy_trip.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface UserRepository {
    void join(UserDto userDto);
    int countByLoginId(String loginId);
    Optional<LoginDto> findByLoginId(@Param("loginId") String loginId);
}
