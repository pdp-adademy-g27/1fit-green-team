package com.example.onefit.subscription.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionResponseDto {

    private UUID id;

    private String days;

    private String freezingDay;

    private Double price;

    private String images;

    private boolean isPopular;
}
