package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.dto.HabitDto;
import com.example.demo.dto.TokenDto;
import com.example.demo.dto.UserNameDto;
import com.example.demo.service.HabitService;
import com.example.demo.service.TokenService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final HabitService habitService;
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirectUri;


    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String googleRedirectUri;

    private final TokenService tokenService;

    @PostMapping("/api/login/oauth2/{provider}")
    public String loginPage(Model model) {
        String kakaoLocation = "https://kauth.kakao.com/oauth/authorize";
        String googleLocation = "https://accounts.google.com";

        model.addAttribute("kakaoLocation", kakaoLocation);
        model.addAttribute("googleLocation", googleLocation);

        return "login";
    }
    @GetMapping("/api/user")
    public ResponseEntity<?> myPage(Authentication authentication){
        System.out.println(authentication.getName());
        Optional<User> exist_user = userService.findBySub(authentication.getName());
        if(exist_user.isEmpty()){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid request");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        User user = exist_user.get();
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/api/user/changeName")
    public ResponseEntity<?> changeName(Authentication authentication, @RequestBody UserNameDto userNameDto){
        Optional<User> existUser = userService.findBySub(authentication.getName());
        if(existUser.isPresent()){
            User user = existUser.get();
            userService.updateName(user, userNameDto.getName());
            Map<String, String> response = new HashMap<>();
            response.put("state", "success");
            return ResponseEntity.ok(response);
        }
        else {
            return ResponseEntity.badRequest().body("fail");
        }
    }

    @PostMapping("/api/user/habits")
    public ResponseEntity<?> settingHabit(Authentication authentication, @RequestBody HabitDto habitDto)
    {
        Optional<User> existUser = userService.findBySub(authentication.getName());
        if(existUser.isPresent()) {
            User user = existUser.get();
            habitService.settingHabit(user, habitDto);
            return ResponseEntity.ok("success");
        }
        else {
            return ResponseEntity.badRequest().body("fail");
        }
    }

    @GetMapping("/api/user/viewHabits")
    public ResponseEntity<?> getHabit(Authentication authentication){
        Optional<User> existUser = userService.findBySub(authentication.getName());
        if(existUser.isPresent()) {
            User user = existUser.get();
            HabitDto habitDto = HabitDto.fromEntity(habitService.gettingHabit(user));
            return ResponseEntity.ok(habitDto);
        }
        else {
            return ResponseEntity.badRequest().body("fail");
        }
    }


    @PostMapping("/api/user/token")
    public ResponseEntity<?> saveDeviceToken(@RequestParam String token, Authentication authentication) {
        String userSub = authentication.getName();
        Optional<User> exist_user = userService.findBySub(userSub);
        if (exist_user.isEmpty()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid user");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        User user = exist_user.get();
        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(token);
        tokenDto.setUserId(user.getId());
        tokenService.saveToken(tokenDto); // 디바이스 토큰 저장
        return ResponseEntity.ok("Token saved successfully");
    }
}

