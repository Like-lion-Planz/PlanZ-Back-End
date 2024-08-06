package com.example.demo.domain;

import com.example.demo.dto.SleepLogDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Year;

@Table(name = "calendar")
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="year")
    private Long year;
    @Column(name="month")
    private Long month;
    @Column(name="day")
    private Long day;

    @Column(name="total_sleep_time")
    private Long totalSleepTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routine_id")
    private Routine routine;

    @Column(name="mood")
    @Enumerated(EnumType.STRING)
    private Mood mood;

    public static Calendar makeCalendar(Long year, Long month, Long day, User user){
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setUser(user);
        return calendar;
    }

    public static Calendar createCalendar(LocalDate date, User user, Routine routine, Mood mood, Long totalSleepTime) {
        Calendar calendar = new Calendar();
        calendar.setYear((long) date.getYear());
        calendar.setMonth((long) date.getMonthValue());
        calendar.setDay((long) date.getDayOfMonth());
        calendar.setUser(user);
        calendar.setRoutine(routine);
        calendar.setMood(mood);
        calendar.setTotalSleepTime(totalSleepTime);
        return calendar;
    }

    public void setFromSleepLogDto(SleepLogDto sleepLogDto){
        this.year = (long)sleepLogDto.getDate().getYear();
        this.month = (long)sleepLogDto.getDate().getMonthValue();
        this.day = (long)sleepLogDto.getDate().getDayOfMonth();
        this.totalSleepTime = sleepLogDto.getSleepTime();
        this.mood = sleepLogDto.getMood();
    }
}