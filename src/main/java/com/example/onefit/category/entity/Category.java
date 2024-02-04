package com.example.onefit.category.entity;

import com.example.onefit.course.entity.Course;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`category`")
public class Category {

    @Id
    private UUID id;
    private String name;
    private String minImage;
    private String bigImage;

    @ManyToMany(mappedBy = "categories")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Course> courses;
}
