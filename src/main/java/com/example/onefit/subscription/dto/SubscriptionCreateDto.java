package com.example.onefit.subscription.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionCreateDto {
    @NotBlank
    private String days;
    @NotBlank
    private String freezingDay;
    @NotBlank
    private Double price;
    @NotBlank
    private String images;

    private boolean isPopular;
}
