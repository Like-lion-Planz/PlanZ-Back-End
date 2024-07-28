package com.example.demo.dto;

import com.example.demo.domain.Routine;
import com.example.demo.domain.ToDo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class RoutineInfoDto {
    private LocalTime endTime;
    private LocalTime startTime;
    private List<ToDoInfoDto> toDoList;
    private String name;
    private Long id;
    @Builder
    public RoutineInfoDto(Routine routine){
        this.id = routine.getId();
        this.endTime = routine.getEndTime();
        this.name = routine.getName();
        this.startTime = routine.getStartTime();
        this.toDoList = routine.getToDoList().stream()
                .map(ToDoInfoDto::new)
                .collect(Collectors.toList());
    }
//    public RoutineInfoDto(Routine routine){
//        this.name = routine.getName();
//        this.id = routine.getId();
//
//    }

}
