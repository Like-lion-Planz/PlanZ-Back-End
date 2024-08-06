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
    private LocalTime time;
    private String content;
    private Long toDoId;
    private boolean notification_is_true;

    @Builder
    public ToDoInfoDto(ToDo toDo){
        this.toDoId = toDo.getId();
        this.content = toDo.getContent();
        this.time = toDo.getNotification();
        this.notification_is_true = toDo.isNotificationIsTrue();
    }

}


