package com.example.onefit.category.dto;

import com.example.onefit.course.dto.CourseResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDto extends CategoryBaseDto {
    private UUID id;
    private Set<CourseResponseDto> course;
}
