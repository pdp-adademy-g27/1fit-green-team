package com.example.onefit.facilities.dto;

import com.example.onefit.course.dto.CourseResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacilitiesResponseDto {

    private UUID id;
    private String name;

    private String image;
    private Set<CourseResponseDto> courses;
}
