package com.example.demo.dto;

import com.example.demo.domain.Habit;
import com.example.demo.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HabitDto {
    private boolean alcohol;

    private boolean coenzyme;

    private boolean coffee;

    private boolean electronicDevices;

    private boolean exerciseSupplements;

    private boolean notApplicable;

    private boolean omega3;

    private boolean smoking;

    private boolean tonic;

    private boolean vitaminB;

    private boolean vitaminD;

    public static HabitDto fromEntity(Habit habit) {
        HabitDto habitDto = new HabitDto();
        habitDto.alcohol = habit.isAlcohol();
        habitDto.coenzyme = habit.isCoenzyme();
        habitDto.coffee = habit.isCoffee();
        habitDto.electronicDevices = habit.isElectronicDevices();
        habitDto.exerciseSupplements = habit.isExerciseSupplements();
        habitDto.notApplicable = habit.isNotApplicable();
        habitDto.omega3 = habit.isOmega3();
        habitDto.smoking = habit.isSmoking();
        habitDto.tonic = habit.isTonic();
        habitDto.vitaminB = habit.isVitaminB();
        habitDto.vitaminD = habit.isVitaminD();
        return habitDto;
    }
}