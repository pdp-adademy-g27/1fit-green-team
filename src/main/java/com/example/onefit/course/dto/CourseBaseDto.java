package com.example.onefit.course.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseBaseDto {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private boolean isFemale;
}
