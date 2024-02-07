package com.example.onefit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoingToLessonDto {
    private UUID userId;
    private UUID courseId;
    private UUID studioId;
    private UUID activityId;
}
