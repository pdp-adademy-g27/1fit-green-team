package com.example.onefit.subscription.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionCreateDto {

    private Integer days;

    private Integer freezingDay;

    private Double price;

    private String images;

    private boolean isPopular;
}
