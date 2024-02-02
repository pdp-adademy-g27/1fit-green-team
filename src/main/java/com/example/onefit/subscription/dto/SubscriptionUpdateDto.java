package com.example.onefit.subscription.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionUpdateDto {

    private UUID id;

    private Integer days;

    private Integer freezingDay;

    private Double price;

    private String images;

}
