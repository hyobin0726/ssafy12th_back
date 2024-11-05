package com.trip.enjoy_trip.service;

import com.trip.enjoy_trip.dto.LoginDto;
import com.trip.enjoy_trip.dto.TokenDto;
import com.trip.enjoy_trip.dto.UserDto;
import jakarta.servlet.http.HttpSession;

public interface UserService {
    void join(UserDto user);
    boolean checkLoginId(String loginId);
    TokenDto loginUser(LoginDto loginDto);
    void logout(String loginId);
}
