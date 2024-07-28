package com.example.demo.controller;

import com.example.demo.domain.Routine;
import com.example.demo.dto.RoutineInfoDto;
import com.example.demo.dto.RoutineSaveDto;
import com.example.demo.global.ResponseTemplate;
import com.example.demo.service.RoutineService;
import com.google.api.gax.paging.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class RoutineController {
    private final RoutineService routineService;
    @PostMapping("/api/createRoutine")
    public ResponseTemplate<?> createRoutine(@RequestBody RoutineSaveDto routineSaveDto){
        return new ResponseTemplate<>(HttpStatus.OK, "루틴 생성 성공",routineService.createRoutine(routineSaveDto));

    }
    @GetMapping("/api/routine/{routineId}")
    public ResponseTemplate<?> searchRoutine(@PathVariable("routineId")Long id){
        return new ResponseTemplate<>(HttpStatus.OK, "해당 루틴 조회 성공",routineService.getMyRoutine(id));
    }
    @GetMapping("/api/routine")
    public ResponseTemplate<?> myAllRoutine(){
        return new ResponseTemplate<>(HttpStatus.OK,"모든 루틴 조회 성공",routineService.myAllRoutine());
    }
    @GetMapping("/api/todaysSchedule")
    public ResponseTemplate<List<RoutineInfoDto>> getTodaysSchedule() {
        List<RoutineInfoDto> todaysSchedule = routineService.getTodaysSchedule();
        return new ResponseTemplate<>(HttpStatus.OK, "오늘의 스케줄 조회 성공", todaysSchedule);
    }

    @GetMapping("/api/schedule/{date}")
    public ResponseTemplate<List<RoutineInfoDto>> getScheduleByDate(@PathVariable("date") String dateStr) {
        LocalDate date = LocalDate.parse(dateStr); // 날짜 문자열을 LocalDate로 변환
        List<RoutineInfoDto> schedule = routineService.getScheduleByDate(date);
        return new ResponseTemplate<>(HttpStatus.OK, "스케줄 조회 성공", schedule);
    }
}
