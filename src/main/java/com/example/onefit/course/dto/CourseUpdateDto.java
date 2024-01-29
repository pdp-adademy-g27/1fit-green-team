package com.example.onefit.course.dto;

import com.example.onefit.active.entity.Activity;
import com.example.onefit.category.entity.Category;
import com.example.onefit.facilities.entity.Facilities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseUpdateDto extends CourseBaseDto{
    private Set<Facilities> facilities;
    private Set<Category> categories;
    private Set<Activity> activities;
}
