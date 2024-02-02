package com.example.onefit.user.dto;

import com.example.onefit.active.entity.Activity;
import com.example.onefit.course.entity.Course;
import com.example.onefit.subscription.dto.SubscriptionResponseDto;
import com.example.onefit.subscription.entity.Subscription;
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

    private Set<Course> courses;

    private Set<Activity> activities;

    private Subscription subscription;

}
