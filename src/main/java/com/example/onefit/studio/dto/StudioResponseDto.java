package com.example.onefit.studio.dto;

import com.example.onefit.active.entity.Activity;
import com.example.onefit.course.entity.Course;
import com.example.onefit.location.entity.Location;
import com.example.onefit.rating.entity.Rating;
import com.example.onefit.subscription.entity.Subscription;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudioResponseDto {
    private UUID id;

    private String name;

    private String description;

    private Location location;

    private Set<Rating> rating;

    private boolean isFemale;

    private Set<Activity> activities;

    private Subscription subscription;

    private Set<Course> courses;

    private LocalDateTime created;

    private LocalDateTime updated;
}
