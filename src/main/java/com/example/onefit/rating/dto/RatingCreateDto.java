package com.example.onefit.rating.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingCreateDto {
    @NotBlank
    private Integer star;
    @NotBlank
    private String comment;
}
