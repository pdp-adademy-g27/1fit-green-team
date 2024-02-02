package com.example.onefit.course.entity;

import com.example.onefit.active.entity.Activity;
import com.example.onefit.category.entity.Category;
import com.example.onefit.facilities.entity.Facilities;
import com.example.onefit.location.entity.Location;
import com.example.onefit.rating.entity.Rating;
import com.example.onefit.studio.entity.Studio;
import com.example.onefit.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`course`")
public class Course {

    @Id
    private UUID id;
    private String name;
    private String description;
    private boolean isFemale;

    @OneToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Location location;


    @ManyToMany(mappedBy = "courses")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<User> users;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "course")
    private Set<Rating> ratings;


    @ManyToMany(mappedBy = "courses")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Category> categories;



    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "course_activity",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id")
    )
    private Set<Activity> activities;



    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "course_facilities",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "facilities_id")
    )
    private Set<Facilities> facilities;


    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Studio studio;
}
