package com.example.demo.dto;

import com.example.demo.domain.Routine;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record RoutineSaveDto(String title, LocalTime startTime, LocalTime endTime, List<LocalDate> dates) {
    public Routine toEntity() {
        return new Routine(title, startTime, endTime, dates);
    }
}