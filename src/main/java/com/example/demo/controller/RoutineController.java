package com.example.demo.controller;

import com.example.demo.dto.RoutineInfoDto;
import com.example.demo.dto.RoutineSaveDto;
import com.example.demo.global.ResponseTemplate;
import com.example.demo.service.RoutineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoutineController {
    private final RoutineService routineService;
    @PostMapping("/api/createRoutine")
    public ResponseEntity<?> createRoutine(@RequestBody RoutineSaveDto routineSaveDto) throws IOException {
        routineService.createRoutine(routineSaveDto);
        return ResponseEntity.ok("success");
        //return new ResponseTemplate<>(HttpStatus.OK, "루틴 생성 성공",routineService.createRoutine(routineSaveDto));
    }

    @GetMapping("/api/routine/{routineId}")
    public ResponseEntity<?> searchRoutine(@PathVariable("routineId")Long id){
        RoutineInfoDto routineInfoDto = routineService.getMyRoutine(id);
        return ResponseEntity.ok(routineInfoDto);
    }

    @GetMapping("/api/routine")
    public ResponseEntity<List<RoutineInfoDto>> myAllRoutine(){
        List<RoutineInfoDto> routineInfoDtos = routineService.myAllRoutine();
        return ResponseEntity.ok(routineInfoDtos);
        //return new ResponseTemplate<>(HttpStatus.OK,"모든 루틴 조회 성공",routineService.myAllRoutine());
    }

    @GetMapping("/api/todaysSchedule")
    public  ResponseEntity<?> getTodaysSchedule() {
        List<RoutineInfoDto> routineInfoDtos = routineService.getTodaysSchedule();
        return ResponseEntity.ok(routineInfoDtos);
        //return new ResponseTemplate<>(HttpStatus.OK, "오늘의 스케줄 조회 성공",routineService.getTodaysSchedule());
    }

    @GetMapping("/api/schedule/{date}")
    public ResponseEntity<?> getScheduleByDate(@PathVariable("date") String dateStr) {
        LocalDate date = LocalDate.parse(dateStr); // 날짜 문자열을 LocalDate로 변환
        List<RoutineInfoDto> routineInfoDtos = routineService.getScheduleByDate(date);
        return ResponseEntity.ok(routineInfoDtos);
        //return new ResponseTemplate<>(HttpStatus.OK, "스케줄 조회 성공", routineService.getScheduleByDate(date));
    }

}
