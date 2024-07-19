package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.dto.KakaoUserInfoResponseDto;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.dto.UserTokenDto;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.KakaoService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class KakaoController {
    private final KakaoService kakaoService;
    private final UserRepository userRepository;
    private final UserService userService;
    @GetMapping("api/login/oauth2/kakao")
    public ResponseEntity<?> callback(@RequestParam("code") String code) {
        //코드로 토큰 받음
        String accessToken = kakaoService.getAccessTokenFromKakao(code);
        KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(accessToken);
        User user = userRepository.findBySub(userInfo.getId());

        UserTokenDto usertokenDto = userService.publish_token(user);
        UserResponseDto userResponseDto = new UserResponseDto(user.getId(), user.getName(), user.getEmail(), usertokenDto);
        return ResponseEntity.ok(userResponseDto);
    }
}
