package com.example.demo.service;

import com.example.demo.domain.ToDo;
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
    private CalendarRepository calendarRepository;
    private TokenRepository tokenRepository;
    private ToDoRepository toDoRepository;

    private FcmService fcmService;
    private RoutineRepository routineRepository;

    @Scheduled(fixedRate = 60_000)
    public void checkNotification(){
        List<ToDo> toDoList = toDoRepository.findAll();
        LocalTime now = LocalTime.now().withSecond(0).withNano(0);

        for (ToDo todo : toDoList) {
            if (now.equals(todo.getNotification())) {
                String token = tokenRepository.findTokenById(todo.getRoutine().getUser().getId());
                if (token != null) {
                    fcmService.sendPushNotification(token, "Reminder", todo.getContent());
                }
            }
        }
    }
}
