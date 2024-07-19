package com.example.demo.repository;

import com.example.demo.domain.Routine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoutineRepository extends JpaRepository<Routine,Long> {
}
