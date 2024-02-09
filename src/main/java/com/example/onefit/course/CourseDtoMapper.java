package com.example.onefit.course;

import com.example.onefit.active.ActivityDtoMapper;
import com.example.onefit.active.dto.ActivityResponseDto;

import com.example.onefit.category.CategoryDtoMapper;
import com.example.onefit.category.dto.CategoryResponseDto;
import com.example.onefit.common.mapper.GenericMapper;
import com.example.onefit.course.dto.CourseCreateDto;
import com.example.onefit.course.dto.CourseResponseDto;
import com.example.onefit.course.dto.CourseUpdateDto;
import com.example.onefit.course.entity.Course;
import com.example.onefit.facilities.FacilitiesDtoMapper;

import com.example.onefit.facilities.dto.FacilitiesResponseDto;

import com.example.onefit.location.LocationDtoMapper;
import com.example.onefit.location.dto.LocationResponseDto;
import com.example.onefit.location.entity.Location;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class CourseDtoMapper extends GenericMapper<Course, CourseCreateDto, CourseResponseDto, CourseUpdateDto> {
    private final ModelMapper modelMapper;
    private final CategoryDtoMapper categoryDtoMapper;
    private final FacilitiesDtoMapper facilitiesDtoMapper;
    private final ActivityDtoMapper activityDtoMapper;
    private final LocationDtoMapper locationDtoMapper;

    @Override
    protected ModelMapper getMapper() {
        return modelMapper;
    }


    @Override
    public Course toEntity(CourseCreateDto courseCreateDto) {
        return modelMapper.map(courseCreateDto, Course.class);
    }


    @Override
    public void toUpdate(CourseUpdateDto courseUpdateDto, Course course) {
        modelMapper.map(courseUpdateDto, course);
    }


    @Override
    public CourseResponseDto toResponse(Course course) {
        CourseResponseDto courseResponseDto = modelMapper.map(course, CourseResponseDto.class);

        if (course.getFacilities() != null) {
            Set<FacilitiesResponseDto> facilities = course
                    .getFacilities()
                    .stream()
                    .map(facilitiesDtoMapper::toResponse)
                    .collect(Collectors.toSet());

            if (course.getCategories() != null) {
                Set<CategoryResponseDto> categories = course
                        .getCategories()
                        .stream()
                        .map(categoryDtoMapper::toResponse)
                        .collect(Collectors.toSet());

                if (course.getActivities() != null) {
                    Set<ActivityResponseDto> activities = course
                            .getActivities()
                            .stream()
                            .map(activityDtoMapper::toResponse)
                            .collect(Collectors.toSet());

                    if (course.getLocation() != null) {
                        Location response = course.getLocation();
                        LocationResponseDto location = locationDtoMapper.toResponse(response);

                        courseResponseDto.setCategories(categories);
                        courseResponseDto.setFacilities(facilities);
                        courseResponseDto.setActivities(activities);
                        courseResponseDto.setLocationResponseDto(location);
                    }
                }
            }
        }

        return courseResponseDto;
    }
}

