package com.example.onefit.course;

import com.example.onefit.common.mapper.GenericMapper;
import com.example.onefit.course.dto.CourseCreateDto;
import com.example.onefit.course.dto.CourseResponseDto;
import com.example.onefit.course.dto.CourseUpdateDto;
import com.example.onefit.course.entity.Course;
import com.example.onefit.facilities.FacilitiesDtoMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CourseDtoMapper extends GenericMapper<Course, CourseCreateDto, CourseResponseDto, CourseUpdateDto> {
    private final ModelMapper modelMapper;

    @Override
    protected ModelMapper getMapper() {
        return modelMapper;
    }

    @Override
    public Course toEntity(CourseCreateDto courseCreateDto) {
        return modelMapper.map(courseCreateDto, Course.class);
    }


    @Override
    public CourseResponseDto toResponse(Course course) {
        return modelMapper.map(course, CourseResponseDto.class);
    }


    @Override
    public void toUpdate(CourseUpdateDto courseUpdateDto, Course course) {
        modelMapper.map(courseUpdateDto, course);
    }
}

