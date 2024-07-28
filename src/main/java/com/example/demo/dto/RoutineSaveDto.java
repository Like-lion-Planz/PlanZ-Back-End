package com.example.demo.dto;

import com.example.demo.domain.Routine;

import java.time.LocalTime;
import java.util.Date;

public record RoutineSaveDto(String name, LocalTime startTime, LocalTime endTime) {
    public Routine toEntity() {
        Routine routine = new Routine();
        routine.setName(name);
        routine.setStartTime(startTime);
        routine.setEndTime(endTime);
        return routine;
    }
}
