package com.example.onefit.course.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseBaseDto {
    @NotBlank
    private String name;
    @NotNull
    private String description;
    private boolean isMale;


}
