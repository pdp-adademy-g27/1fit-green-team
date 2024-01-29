package com.example.onefit.course.dto;

import com.example.onefit.active.ActivityResponseDto;
import com.example.onefit.category.dto.CategoryResponseDto;
import com.example.onefit.facilities.dto.FacilitiesResponseDto;
import com.example.onefit.rating.entity.Rating;
import com.example.onefit.saved.entity.Saved;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    private UUID id;
    @NotBlank
    private Set<FacilitiesResponseDto> facilities;
    @NotBlank
    private Set<CategoryResponseDto> categories;
    @NotBlank
    private Set<ActivityResponseDto> activities;
    @NotBlank
    private Set<Rating> ratings;
    @NotBlank
    private Set<Saved> saveds;
    @NotBlank
    private LocalDateTime created;
    @NotBlank
    private LocalDateTime updated;

}
