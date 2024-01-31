package com.example.onefit.category.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryBaseDto {

    @NotNull
    private String name;

    @NotNull
    private String minImage;

    @NotNull
    private String bigImage;
}
