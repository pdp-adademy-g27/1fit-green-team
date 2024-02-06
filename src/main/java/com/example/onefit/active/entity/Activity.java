package com.example.onefit.active.entity;

import com.example.onefit.course.entity.Course;
import com.example.onefit.studio.entity.Studio;
import com.example.onefit.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Activity {
    @Id
    private UUID id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private int countLesson;

    private  int maxLessons;

    private boolean rateIsActive;

    @ManyToMany(mappedBy = "activities")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Course> courses;

    @ManyToMany(mappedBy = "activities")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Studio> studios;


    @ManyToMany(mappedBy = "activities")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<User> users;
}
