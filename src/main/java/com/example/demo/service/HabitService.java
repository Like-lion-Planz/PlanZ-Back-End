package com.example.demo.service;

import com.example.demo.domain.Habit;
import com.example.demo.domain.User;
import com.example.demo.dto.HabitDto;
import com.example.demo.repository.HabitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class HabitService {
    private final HabitRepository habitRepository;

    public void settingHabit(User user, HabitDto habitDto){
        Habit habit = Habit.fromDto(user, habitDto);
        habitRepository.save(habit);
    }

    public Habit gettingHabit(User user){
        return habitRepository.findByUserId(user);
    }
}