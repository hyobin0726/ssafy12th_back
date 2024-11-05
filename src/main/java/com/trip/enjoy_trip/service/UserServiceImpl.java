package com.trip.enjoy_trip.service;

import com.trip.enjoy_trip.dto.LoginDto;
import com.trip.enjoy_trip.dto.UserDto;
import com.trip.enjoy_trip.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
//
        userRepository.join(userDto);
    }
    @Override
    public boolean checkLoginId(String loginId){
        return userRepository.countByLoginId(loginId) > 0;

    }

    @Override
    public String loginUser(LoginDto loginDto, HttpSession session) {
        // 로그인 ID로 사용자 정보 조회
        LoginDto dbuser = userRepository.findByLoginId(loginDto.getLoginId())
                .orElseThrow(() -> new RuntimeException("가입되지 않은 회원입니다."));

        if (loginDto.getPassword().equals(dbuser.getPassword())) {
            session.setAttribute("user", dbuser);  // 세션에 사용자 정보 저장
            return "로그인에 성공하였습니다.";
        } else {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
    }

}
