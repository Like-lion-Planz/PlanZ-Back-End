package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Setter;

import java.time.LocalDate;

import java.time.LocalTime;

@Table(name = "calendar")
@Entity
@Getter

@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name="year")
//    private int year;
//    @Column(name="month")
//    private int month;
//    @Column(name="day")
//    private int day;
    @Column(name="date")
    private LocalDate date;

    @Column(name="year")
    private int year;
    @Column(name="month")
    private int month;
    @Column(name="day")
    private int day;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "routine_id")
    private Routine routine;

    @Enumerated(EnumType.STRING)
    private Mood mood;

    @Column
    private LocalTime totalSleep;

    public Calendar(LocalDate date, User user, Routine routine, Mood mood, LocalTime totalSleep) {
        this.date = date;
        this.user = user;
        this.routine = routine;
        this.mood = mood;
        this.totalSleep = totalSleep;
    }

}
