package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.dto.CalendarSaveDto;
import com.example.demo.dto.ResponseSleepLogDto;
import com.example.demo.dto.SleepLogDto;
import com.example.demo.global.ResponseTemplate;
import com.example.demo.service.CalendarService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CalendarController {
    private final CalendarService calendarService;
    private final UserService userService;

    @GetMapping("/api/sleepLog/")
    public ResponseEntity<?> viewSleepLog(Authentication authentication, @RequestParam LocalDateTime date){
        Optional<User> existUser = userService.findBySub(authentication.getName());
        if(existUser.isPresent()) {
            User user = existUser.get();
            List<ResponseSleepLogDto> responseSleepLogDtos = calendarService.viewAll(user, date);
            return ResponseEntity.ok(responseSleepLogDtos);
        }
        else{
            return ResponseEntity.badRequest().body("fail");
        }
    }

    @GetMapping("/api/sleepDetailLog/")
    public ResponseEntity<?> viewSleepDetailLog(Authentication authentication, @RequestParam LocalDateTime date){
        Optional<User> existUser = userService.findBySub(authentication.getName());
        if(existUser.isPresent()) {
            User user = existUser.get();
            ResponseSleepLogDto responseSleepLogDto = calendarService.viewDetail(user, date);
            return ResponseEntity.ok(responseSleepLogDto);
        }
        else{
            return ResponseEntity.badRequest().body("fail");
        }
    }


    @PostMapping("/api/sleepLog/addLog")
    public ResponseEntity<?> addSleepLog(Authentication authentication, @RequestBody SleepLogDto sleepLogDto){
        Optional<User> existUser = userService.findBySub(authentication.getName());
        if(existUser.isPresent()) {
            User user = existUser.get();
            calendarService.addSleepLog(user, sleepLogDto);
            return ResponseEntity.ok("success");
        }else{
            return ResponseEntity.badRequest().body("fail");
        }
    }

    @PostMapping("/api/saveRoutineDates")
    public ResponseEntity<?> saveRoutineDates(@RequestBody CalendarSaveDto calendarSaveDto) {
        if(calendarService.saveRoutineDates(calendarSaveDto)){
            return ResponseEntity.ok("루틴 날짜 저장 성공");
            //return new ResponseTemplate<>(HttpStatus.OK, "루틴 날짜 저장 성공");
        }
        return ResponseEntity.badRequest().body("루틴 날짜 저장 실패");
        //return new ResponseTemplate<>(HttpStatus.OK, "루틴 날짜 저장 실패");
    }
}
