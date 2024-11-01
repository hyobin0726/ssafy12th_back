package com.trip.enjoy_trip.service;

import com.trip.enjoy_trip.dto.UserDto;
import com.trip.enjoy_trip.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl  implements UserService{
    private final UserRepository userRepository;


    @Override
    public void join(UserDto userDto) {
        userRepository.join(userDto);
    }
}
