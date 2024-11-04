package com.trip.enjoy_trip.repository;

import com.trip.enjoy_trip.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {
    void join(UserDto userDto);
    int countByLoginId(String loginId);
}
