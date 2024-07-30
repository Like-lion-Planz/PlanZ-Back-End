package com.example.demo.service;

import com.example.demo.domain.Calendar;
import com.example.demo.domain.Routine;
import com.example.demo.domain.ToDo;
import com.example.demo.domain.User;
import com.example.demo.dto.FcmServiceDto;
import com.example.demo.repository.CalendarRepository;
import com.example.demo.repository.RoutineRepository;
import com.example.demo.repository.ToDoRepository;
import com.example.demo.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final CalendarRepository calendarRepository;
    private final TokenRepository tokenRepgitrository;
    private final ToDoRepository toDoRepository;

    private final UserService userService;
    private final FcmService fcmService;
    private final RoutineRepository routineRepository;

    @Scheduled(fixedRate = 60_000)
    public void checkNotification() {

        List<User> userList = userService.findAll();
        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);

        for (User user : userList) {
            List<Routine> routineList = user.getRoutineList();

            for (Routine routine : routineList) {
                List<ToDo> toDoList = toDoRepository.findByRoutine(routine);
                for (ToDo toDo : toDoList) {
                    // Find all calendars for this routine
                    List<Calendar> calendars = calendarRepository.findByRoutine(routine);
                    for (Calendar calendar : calendars) {
                        LocalDateTime todoDateTime = LocalDateTime.of(
                                Math.toIntExact(calendar.getYear()),
                                Math.toIntExact(calendar.getMonth()),
                                Math.toIntExact(calendar.getDay()),
                                (int) toDo.getNotification().getHour(),
                                (int) toDo.getNotification().getMinute()
                        );
                        if (now.equals(todoDateTime)) {
                            String token = tokenRepgitrository.findTokenById(user);

                            if (token != null) {
                                fcmService.sendPushNotification(token, user.getName(), toDo.getContent());
                            }
                        }
                    }
                }
            }
        }
    }
}
