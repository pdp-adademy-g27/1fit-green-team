package com.example.onefit.rating.dto;

import com.example.onefit.course.entity.Course;
import com.example.onefit.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingResponseDto {
    private UUID id;
    private Integer star;
    private String comment;
    private User user;
    private Course course;

}
