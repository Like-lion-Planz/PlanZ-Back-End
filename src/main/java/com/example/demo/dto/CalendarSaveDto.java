package com.example.demo.dto;

import java.time.LocalDate;
import java.util.List;

public class CalendarSaveDto {
    private Long routineId;
    private List<LocalDate> dates;

    public Long getRoutineId() {
        return routineId;
    }

    public void setRoutineId(Long routineId) {
        this.routineId = routineId;
    }

    public List<LocalDate> getDates() {
        return dates;
    }

    public void setDates(List<LocalDate> dates) {
        this.dates = dates;
    }
}
