package com.example.onefit.rating.entity;

import com.example.onefit.course.entity.Course;
import com.example.onefit.studio.entity.Studio;
import com.example.onefit.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`rating`")
public class Rating {

    @Id
    private UUID id;
    private Integer star;
    private String comment;
    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;
    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Course course;
    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Studio studio;
}
