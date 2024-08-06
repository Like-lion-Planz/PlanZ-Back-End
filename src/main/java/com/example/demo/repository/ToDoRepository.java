package com.example.demo.repository;

import com.example.demo.domain.Routine;
import com.example.demo.domain.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToDoRepository extends JpaRepository<ToDo,Long> {
    List<ToDo> findByRoutine(Routine routine);
}