package com.example.onefit.studio.dto;

import com.example.onefit.active.entity.Activity;
import com.example.onefit.location.entity.Location;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudioCreateDto {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private Location location;

    @NotBlank
    private Set<Activity> activities;

    private boolean isFemale;



}
