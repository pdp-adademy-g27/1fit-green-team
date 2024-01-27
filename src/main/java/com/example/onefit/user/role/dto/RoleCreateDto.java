package com.example.onefit.user.role.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleCreateDto {
    @NotBlank
    private String name;
    @NotEmpty(message = "permissions cannot be empty")
    private Set<String> permissions;


}
