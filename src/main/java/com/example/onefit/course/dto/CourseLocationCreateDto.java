package com.example.onefit.course.dto;

import com.example.onefit.location.dto.LocationCreateDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseLocationCreateDto {

    private CourseCreateDto course;
    private LocationCreateDto location;
}
