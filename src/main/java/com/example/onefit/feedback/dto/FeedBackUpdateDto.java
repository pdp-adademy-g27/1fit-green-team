package com.example.onefit.feedback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedBackUpdateDto {
    private String companyName;
    private String city;
    private String name;
    private String instagram;
}
