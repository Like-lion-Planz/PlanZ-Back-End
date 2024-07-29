package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "routine")
@Getter
@Setter
@Entity
public class Routine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "title")
    private String title;

    @ElementCollection
    @CollectionTable(name = "routine_dates", joinColumns = @JoinColumn(name = "routine_id"))
    @Column(name = "date")
    private List<LocalDate> date = new ArrayList<>();

    @OneToMany(mappedBy = "routine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Calendar> calendarList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "routine")
    private List<ToDo> toDoList = new ArrayList<>();

    // 기본 생성자
    public Routine() {
    }

    // 생성자
    public Routine(String title, LocalTime startTime, LocalTime endTime) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // 생성자
    public Routine(String title, LocalTime startTime, LocalTime endTime, List<LocalDate> dates) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = dates;
    }

}
