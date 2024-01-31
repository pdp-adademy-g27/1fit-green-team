package com.example.onefit.feedback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedBackResponseDto {
    private UUID id;
    private String companyName;
    private String name;
}
