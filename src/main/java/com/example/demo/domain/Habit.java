package com.example.demo.domain;

import com.example.demo.dto.HabitDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "habit")
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(name = "alcohol")
    private boolean alcohol;

    @Column(name = "coenzyme")
    private boolean coenzyme;

    @Column(name = "coffee")
    private boolean coffee;

    @Column(name = "electronic_devices")
    private boolean electronicDevices;

    @Column(name = "exercise_supplements")
    private boolean exerciseSupplements;

    @Column(name = "not_applicable")
    private boolean notApplicable;

    @Column(name = "omega3")
    private boolean omega3;

    @Column(name = "smoking")
    private boolean smoking;

    @Column(name = "tonic")
    private boolean tonic;

    @Column(name = "vitaminb")
    private boolean vitaminB;

    @Column(name = "vitamind")
    private boolean vitaminD;

    public static Habit fromDto(User user, HabitDto habitDto) {
        Habit habit = new Habit();
        habit.userId = user;
        habit.alcohol = habitDto.isAlcohol();
        habit.coenzyme = habitDto.isCoenzyme();
        habit.coffee = habitDto.isCoffee();
        habit.electronicDevices = habitDto.isElectronicDevices();
        habit.exerciseSupplements = habitDto.isExerciseSupplements();
        habit.notApplicable = habitDto.isNotApplicable();
        habit.omega3 = habitDto.isOmega3();
        habit.smoking = habitDto.isSmoking();
        habit.tonic = habitDto.isTonic();
        habit.vitaminB = habitDto.isVitaminB();
        habit.vitaminD = habitDto.isVitaminD();
        return habit;
    }
}