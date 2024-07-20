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
        LocalTime now = LocalTime.now().withSecond(0).withNano(0);

        for (User user : userList) {
            Routine routine = calendarRepository.findByUser(user);
            if (routine != null) {
                List<ToDo> toDoList = toDoRepository.findByRoutine(routine);
                for (ToDo todo : toDoList) {
                    if (now.equals(todo.getNotification())) {
                        String token = tokenRepgitrository.findTokenById(user);

                        if (token != null) {
                            fcmService.sendPushNotification(token, user.getName(), todo.getContent());
                        }
                    }

                }
            }
        }
    }
}
