package com.example.onefit.facilities.entity;

import com.example.onefit.course.entity.Course;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`facilities`")
public class Facilities {

    @Id
    private UUID id;

    private String name;

    private String image;

    @ManyToMany(mappedBy = "facilities")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Course> courses;
}
