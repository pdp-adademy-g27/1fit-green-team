package com.example.onefit.facilities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacilitiesResponseDto {

    private UUID id;
    private String name;

    private String image;
}
