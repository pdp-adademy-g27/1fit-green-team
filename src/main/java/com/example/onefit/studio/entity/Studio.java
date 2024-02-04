package com.example.onefit.studio.entity;

import com.example.onefit.active.entity.Activity;
import com.example.onefit.course.entity.Course;
import com.example.onefit.location.entity.Location;
import com.example.onefit.rating.entity.Rating;
import com.example.onefit.subscription.entity.Subscription;
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
@Table(name = "`studio`")
@EntityListeners(AuditingEntityListener.class)
public class Studio {

    @Id
    private UUID id;
    private String name;
    private String description;

    @CreatedDate
    private LocalDateTime created;

    @LastModifiedDate
    private LocalDateTime updated;

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
