package com.trip.enjoy_trip.service;

import com.trip.enjoy_trip.dto.LoginDto;
import com.trip.enjoy_trip.dto.TokenDto;
import com.trip.enjoy_trip.dto.UserDto;
import com.trip.enjoy_trip.repository.UserRepository;
import com.trip.enjoy_trip.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl  implements UserService{
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenService jwtTokenService; // JWT 관련 로직을 담당하는 JwtTokenService

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
//
        userRepository.join(userDto);
    }
    @Override
    public boolean checkLoginId(String loginId){
        return userRepository.countByLoginId(loginId) > 0;

    }

    @Override
    public TokenDto loginUser(LoginDto loginDto) {
        // 로그인 ID로 사용자 정보 조회
        LoginDto dbUser = userRepository.findByLoginId(loginDto.getLoginId())
                .orElseThrow(() -> new RuntimeException("가입되지 않은 회원입니다."));

        // 비밀번호 일치 확인
        if (loginDto.getPassword().equals(dbUser.getPassword())) {
            // JWT 토큰 발급을 JwtTokenService에 위임
            return jwtTokenService.generateTokens(dbUser.getUserId());
        } else {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
    }
    @Override
    public void logout(Integer userId) {
        jwtTokenService.deleteRefreshToken(userId);
    }

    @Override
    public UserDto getUserInfo(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }

}
