package com.example.demo.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "habit")
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private boolean coffee;
    @Column
    private boolean alcohol;
    @Column
    private boolean smoking;
    @Column
    private boolean electronicDevices;
    @Column
    private boolean tonic;

    @Column
    private boolean vitaminB;

    @Column
    private boolean vitaminD;

    @Column
    private boolean omega3;


    @Column
    private boolean coenzyme;

    @Column
    private boolean exercise_supplements;

    @Column
    private boolean not_applicable;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}