package com.example.onefit.studio.dto;


import com.example.onefit.location.dto.LocationCreateDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudioCreateDto {

    @NotBlank
    private String name;

    @NotNull
    private String description;

    private LocationCreateDto locationCreateDto;

    private boolean isFemale;



}
