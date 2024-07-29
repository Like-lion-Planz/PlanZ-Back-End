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
    private List<ToDoInfoDto> toDoList;
    private String routineName;
    private Long routineId;
    @Builder
    public RoutineInfoDto(Routine routine,Long id){
        this.routineId = routine.getId();
        this.routineName = routine.getTitle();
        this.toDoList = routine.getToDoList().stream()
                .map(ToDoInfoDto::new)
                .collect(Collectors.toList());
    }
    public RoutineInfoDto(Routine routine){
        this.routineName = routine.getTitle();
        this.routineId = routine.getId();
        this.toDoList=null;
    }

}
