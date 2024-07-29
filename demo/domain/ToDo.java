package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="notification")
    private LocalTime notification;
    @Column(name="content")
    private String content;
    @ManyToOne
    @JoinColumn(name = "calendar_id")
    private Calendar calendar;
    @Column
    private boolean notificationIsTrue=true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalTime getNotification() {
        return notification;
    }

    public void setNotification(LocalTime notification) {
        this.notification = notification;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public boolean isNotificationIsTrue() {
        return notificationIsTrue;
    }

    public void setNotificationIsTrue(boolean notificationIsTrue) {
        this.notificationIsTrue = notificationIsTrue;
    }

    public Routine getRoutine() {
        return routine;
    }

    public void setRoutine(Routine routine) {
        this.routine = routine;
    }



    @ManyToOne
    @JoinColumn(name = "routine_id")
    private Routine routine;


}
