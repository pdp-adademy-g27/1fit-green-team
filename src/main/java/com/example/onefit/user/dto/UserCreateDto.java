package com.example.onefit.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserCreateDto extends UserBaseDto {
    @NotBlank
    private String password;

    private LocalDateTime created;

    private LocalDateTime updated;



}
