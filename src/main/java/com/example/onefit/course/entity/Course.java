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

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`course`")
@EntityListeners(AuditingEntityListener.class)
public class Course {

    @Id
    private UUID id;
    private String name;
    private String description;
    private boolean isMale;

    @CreatedDate
    private LocalDateTime created;

    @LastModifiedDate
    private LocalDateTime updated;

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





    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "course_category",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
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
