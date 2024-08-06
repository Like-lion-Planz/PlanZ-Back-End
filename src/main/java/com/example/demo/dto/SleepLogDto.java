package com.example.demo.dto;

import com.example.demo.domain.Mood;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SleepLogDto {
    private LocalDateTime date;
    private Mood mood;
    private Long sleepTime;
}