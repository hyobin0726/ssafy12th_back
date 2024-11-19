package com.trip.enjoy_trip.service;

import com.trip.enjoy_trip.dto.LoginDto;
import com.trip.enjoy_trip.dto.TokenDto;
import com.trip.enjoy_trip.dto.UserDto;

public interface UserService {
    void join(UserDto user);
    boolean checkLoginId(String loginId);
    TokenDto loginUser(LoginDto loginDto);
    void logout(Integer userId);
    UserDto getUserInfo(Integer userId);
    UserDto getOtherUserProfile(Integer userId);
    UserDto SearchOther(String loginId);
}
