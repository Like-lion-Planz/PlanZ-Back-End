package com.example.demo.repository;

import com.example.demo.domain.Calendar;
import com.example.demo.domain.Routine;
import com.example.demo.domain.User;
import com.example.demo.dto.ResponseSleepLogDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface CalendarRepository extends JpaRepository<Calendar,Long> {
    Calendar findByYearAndMonthAndDayAndUser(Long year, Long Month, Long Day, User user);
    List<Calendar> findAllByYearAndMonthAndDayAndUser(Long year, Long Month, Long Day, User user);

    @Modifying
    @Transactional
    @Query("SELECT new com.example.demo.dto.ResponseSleepLogDto(c.day, c.mood) "+
            "FROM Calendar c " +
            "WHERE c.year = :year and c.month = :month and c.user = :user " +
            "ORDER BY c.day")
    List<ResponseSleepLogDto> findAllByYearAndMonth(Long year, Long month, User user);


    List<Calendar> findByRoutine(Routine routine);
}