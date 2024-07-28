package com.example.demo.controller;

import com.example.demo.dto.CalendarSaveDto;
import com.example.demo.global.ResponseTemplate;
import com.example.demo.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CalendarController {
    private final CalendarService calendarService;
    @PostMapping("/api/saveRoutineDates")
    public ResponseTemplate<?> saveRoutineDates(@RequestBody CalendarSaveDto calendarSaveDto) {
        if(calendarService.saveRoutineDates(calendarSaveDto)){
            return new ResponseTemplate<>(HttpStatus.OK, "루틴 날짜 저장 성공");
        }
        return new ResponseTemplate<>(HttpStatus.OK, "루틴 날짜 저장 실패");
    }
}
