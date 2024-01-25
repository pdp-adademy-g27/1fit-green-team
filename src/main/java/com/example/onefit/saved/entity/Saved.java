package com.example.onefit.saved.entity;

import com.example.onefit.course.entity.Course;
import com.example.onefit.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Saved {

    @Id
    private UUID id;

    @ManyToMany(mappedBy = "saveds")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Course> courses;

    @ManyToMany(mappedBy = "saveds")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<User> users;

}
