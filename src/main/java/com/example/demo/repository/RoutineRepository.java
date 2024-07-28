package com.example.demo.repository;

import com.example.demo.domain.Routine;

import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoutineRepository extends JpaRepository<Routine,Long> {
    Routine findRoutineByUserAndId(User user, Long id);

    List<Routine> findRoutineByUser(User user);
}