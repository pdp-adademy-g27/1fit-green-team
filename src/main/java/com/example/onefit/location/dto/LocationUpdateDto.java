package com.example.onefit.location.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationUpdateDto {

    @NotBlank
    private String name;

    @NotBlank
    private double lat;

    @NotBlank
    private double lon;

}
