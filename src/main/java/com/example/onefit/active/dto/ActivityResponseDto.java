package com.example.onefit.active.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityResponseDto {

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private int countLesson;

    private int maxLessons;

    private boolean rateIsActive;
}