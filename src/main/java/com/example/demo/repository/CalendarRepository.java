package com.example.demo.repository;

import com.example.demo.domain.Calendar;
import com.example.demo.domain.Routine;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<Calendar,Long> {
    Routine findByUser(User user);
    Calendar findByRoutine(Routine routine);
}
