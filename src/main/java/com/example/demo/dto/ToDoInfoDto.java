package com.example.demo.dto;

import com.example.demo.domain.Calendar;
import com.example.demo.domain.Routine;
import com.example.demo.domain.ToDo;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ToDoInfoDto {
    private LocalTime notification;
    private String content;
    private Calendar calendar;
    private boolean notificationIsTrue;
    private Routine routine;
    @Builder
    public ToDoInfoDto(ToDo toDo){
        this.notification = toDo.getNotification();
        this.routine = toDo.getRoutine();
        this.content = toDo.getContent();
        this.notificationIsTrue = toDo.isNotificationIsTrue();
    }

}


