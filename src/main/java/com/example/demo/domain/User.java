package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "users")
@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String sub;

    @Column
    private String name;

    @Column
    private String email;

    @Enumerated(EnumType.STRING)
    @Column
    private Provider provider;

    @Column
    private String image;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Calendar> calendarList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Routine> routineList = new ArrayList<>();


    @Builder
    public User(String sub, String name, String email, Provider provider, String image) {
        this.sub = sub;
        this.name = name;
        this.email = email;
        this.provider = provider;
        this.image = image;
    }
}
