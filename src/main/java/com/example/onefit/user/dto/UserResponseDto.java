package com.example.onefit.user.dto;

import com.example.onefit.active.dto.ActivityResponseDto;
import com.example.onefit.course.dto.CourseResponseDto;
import com.example.onefit.subscription.dto.SubscriptionResponseDto;
import com.example.onefit.user.role.dto.RoleResponseDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto extends UserBaseDto{

    private UUID id;

    private LocalDateTime created;

    private LocalDateTime updated;

    private Set<RoleResponseDto> roles;

    private Set<String> permissions;

    private Set<CourseResponseDto> courses;

    private Set<ActivityResponseDto> activities;

    private SubscriptionResponseDto subscriptionResponseDto;


}
