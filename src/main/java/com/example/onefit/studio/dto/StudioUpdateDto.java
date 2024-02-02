package com.example.onefit.studio.dto;

import com.example.onefit.active.entity.Activity;
import com.example.onefit.location.entity.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudioUpdateDto {

    private String name;

    private String description;

    private Location location;

    private Set<Activity> activities;

}
