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
//    private final BCryptPasswordEncoder passwordEncoder;


    @Override
    public void join(UserDto userDto) {
//        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
//
//        UserDto encryptedUserDto = UserDto.builder()
//                .loginId(userDto.getLoginId())
//                .password(encodedPassword)
//                .name(userDto.getName())
//                .email(userDto.getEmail())
//                .phone(userDto.getPhone())
//                .birth(userDto.getBirth())
//                .profileUrl(userDto.getProfileUrl())
//                .oneLiner(userDto.getOneLiner())
//                .build();


        userRepository.join(userDto);
    }
}
