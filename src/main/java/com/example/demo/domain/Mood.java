package com.example.demo.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Mood {

    ANNOYED("MOOD_ANNOYED"), LIVELY("MOOD_LIVELY"), NEUTRAL("MOOD_NEUTRAL"),
    REFRESHING("MOOD_REFRESHING"), TIRED("MOOD_TIRED");
    private final String key;
}