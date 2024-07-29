package com.example.demo.repository;

import com.example.demo.domain.Calendar;
import com.example.demo.domain.Routine;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CalendarRepository extends JpaRepository<Calendar,Long> {
    Routine findByUser(User user);

    List<Calendar> findByUserAndDate(User user, LocalDate date);

    List<Calendar> findByRoutine(Routine routine);
}