
package com.example.demo.repository;


import com.example.demo.domain.Habit;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitRepository extends JpaRepository<Habit, Long> {
    Habit findByUserId(User user);
}
