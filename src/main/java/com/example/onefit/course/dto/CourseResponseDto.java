package com.example.onefit.course.dto;

import com.example.onefit.active.dto.ActivityResponseDto;
import com.example.onefit.category.dto.CategoryResponseDto;
import com.example.onefit.facilities.dto.FacilitiesResponseDto;
import com.example.onefit.location.dto.LocationResponseDto;
import com.example.onefit.rating.entity.Rating;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseResponseDto extends CourseBaseDto{

    private UUID id;

    private Set<FacilitiesResponseDto> facilities;

    private Set<CategoryResponseDto> categories;

    private Set<ActivityResponseDto> activities;

    private Set<Rating> ratings;

    private LocationResponseDto locationResponseDto;

    private LocalDateTime created;

    private LocalDateTime updated;


}
