package com.example.onefit.studio.entity;

import com.example.onefit.active.entity.Activity;
import com.example.onefit.course.entity.Course;
import com.example.onefit.location.entity.Location;
import com.example.onefit.rating.entity.Rating;
import com.example.onefit.subscription.entity.Subscription;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`studio`")
public class Studio {

    @Id
    private UUID id;
    private String name;
    private String description;

    @OneToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Location location;

    @OneToMany(mappedBy = "studio")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Rating> rating;
    private boolean isFemale;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "studio_activity",
            joinColumns = @JoinColumn(name = "studio_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id")
    )
    private Set<Activity> activities;

    @OneToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Subscription subscription;

    @OneToMany(mappedBy = "studio")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Course> courses;


}
