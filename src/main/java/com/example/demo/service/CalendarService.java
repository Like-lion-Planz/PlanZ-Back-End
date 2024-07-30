package com.example.demo.service;

import com.example.demo.domain.Calendar;
import com.example.demo.domain.Routine;
import com.example.demo.domain.User;
import com.example.demo.dto.CalendarSaveDto;
import com.example.demo.dto.ResponseSleepLogDto;
import com.example.demo.dto.SleepLogDto;
import com.example.demo.repository.CalendarRepository;
import com.example.demo.repository.RoutineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CalendarService {
    private final RoutineRepository routineRepository;
    private final UserService userService;
    private final CalendarRepository calendarRepository;

    public void addSleepLog(User user, SleepLogDto sleepLogDto){
        Calendar calendar = calendarRepository.findByYearAndMonthAndDayAndUser((long)sleepLogDto.getDate().getYear(), (long)sleepLogDto.getDate().getMonthValue(), (long)sleepLogDto.getDate().getDayOfMonth(), user);
        if(calendar == null){
            calendar = Calendar.makeCalendar((long)sleepLogDto.getDate().getYear(), (long)sleepLogDto.getDate().getMonthValue(), (long)sleepLogDto.getDate().getDayOfMonth(), user);
        }
        calendar.setFromSleepLogDto(sleepLogDto);
        calendarRepository.save(calendar);
    }

    public List<ResponseSleepLogDto> viewAll(User user, LocalDateTime date){
        long year = date.getYear();
        long month = date.getMonthValue();
        return calendarRepository.findAllByYearAndMonth(year,month,user);
    }

    public ResponseSleepLogDto viewDetail(User user, LocalDateTime date){
        long year = date.getYear();
        long month = date.getMonthValue();
        long day = date.getDayOfMonth();
        return ResponseSleepLogDto.fromEntity(calendarRepository.findByYearAndMonthAndDayAndUser(year, month, day, user));
    }
    public boolean saveRoutineDates(CalendarSaveDto calendarSaveDto){
        User user = userService.findByUser();
        Routine routine = routineRepository.findById(calendarSaveDto.getRoutineId())
                .orElseThrow(() -> new RuntimeException("루틴을 찾을 수 없습니다."));

        for (LocalDate date : calendarSaveDto.getDates()) {
            Calendar calendar = Calendar.createCalendar(
                    date,
                    user,
                    routine,
                    null, // Mood는 null로 설정 또는 필요한 경우 기본값을 설정하세요.
                    null  // totalSleepTime은 null로 설정 또는 필요한 경우 기본값을 설정하세요.
            );

            calendarRepository.save(calendar);
        }
        return true;
    }




}
