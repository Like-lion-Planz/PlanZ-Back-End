package com.example.demo.service;

import com.example.demo.domain.Calendar;
import com.example.demo.domain.Routine;
import com.example.demo.domain.User;
import com.example.demo.dto.RoutineInfoDto;
import com.example.demo.dto.RoutineSaveDto;
import com.example.demo.repository.CalendarRepository;
import com.example.demo.repository.RoutineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RoutineService {
    private final RoutineRepository routineRepository;
    private final UserService userService;
    private final CalendarRepository calendarRepository;

    public RoutineInfoDto createRoutine(RoutineSaveDto routineSaveDto){
        User user = userService.findByUser();
        Routine routine = routineSaveDto.toEntity();
        routine.setUser(user);

        routineRepository.save(routine);
        return new RoutineInfoDto(routine);
    }
    public RoutineInfoDto getMyRoutine(Long id){
        User user = userService.findByUser();
        Routine routine = routineRepository.findRoutineByUserAndId(user,id);
        return new RoutineInfoDto(routine);
    }
    public List<RoutineInfoDto> myAllRoutine(){
        User user = userService.findByUser();
        List<Routine> routines = routineRepository.findRoutineByUser(user);
        return routines.stream()
                .map(RoutineInfoDto::new)
                .collect(Collectors.toList());
    }
    public List<RoutineInfoDto> getTodaysSchedule() {
        User user = userService.findByUser();
        LocalDate today = LocalDate.now();
        List<Calendar> calendars = calendarRepository.findByUserAndDate(user, today);

        return calendars.stream()
                .map(calendar -> {
                    Routine routine = calendar.getRoutine();
                    return new RoutineInfoDto(routine);
                })
                .collect(Collectors.toList());
    }
    public List<RoutineInfoDto> getScheduleByDate(LocalDate date) {
        User user = userService.findByUser();
        List<Calendar> calendars = calendarRepository.findByUserAndDate(user, date);

        return calendars.stream()
                .map(calendar -> {
                    Routine routine = calendar.getRoutine();
                    return new RoutineInfoDto(routine);
                })
                .collect(Collectors.toList());
    }
}
