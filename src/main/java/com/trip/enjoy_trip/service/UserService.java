package com.trip.enjoy_trip.service;

import com.trip.enjoy_trip.dto.LoginDto;
import com.trip.enjoy_trip.dto.UserDto;
import jakarta.servlet.http.HttpSession;

public interface UserService {
    void join(UserDto user);
    boolean checkLoginId(String loginId);
    String loginUser(LoginDto loginDto, HttpSession session);
}
