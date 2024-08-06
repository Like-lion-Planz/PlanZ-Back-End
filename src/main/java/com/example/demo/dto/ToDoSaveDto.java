package com.example.demo.dto;

import com.example.demo.domain.Routine;
import com.example.demo.domain.ToDo;

import java.time.LocalTime;

public record ToDoSaveDto(String content, LocalTime time) {
    public ToDo toEntity(){
        ToDo toDo = new ToDo();
        toDo.setContent(content);
        toDo.setNotification(time);
        toDo.setNotificationIsTrue(true);
        return toDo;
    }
}
