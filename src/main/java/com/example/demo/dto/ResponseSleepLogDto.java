package com.example.demo.dto;

import com.example.demo.domain.Calendar;
import com.example.demo.domain.Mood;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseSleepLogDto {
    private Long date;
    private Mood mood;

    public static ResponseSleepLogDto fromEntity(Calendar calendar){
        ResponseSleepLogDto responseSleepLogDto = new ResponseSleepLogDto();
        responseSleepLogDto.date = calendar.getDay();
        responseSleepLogDto.mood = calendar.getMood();
        return responseSleepLogDto;
    }
}