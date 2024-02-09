package com.example.onefit.user;

import com.example.onefit.active.ActivityDtoMapper;
import com.example.onefit.active.dto.ActivityResponseDto;

import com.example.onefit.common.mapper.GenericMapper;
import com.example.onefit.course.CourseDtoMapper;
import com.example.onefit.course.dto.CourseResponseDto;

import com.example.onefit.subscription.SubscriptionDtoMapper;
import com.example.onefit.subscription.dto.SubscriptionResponseDto;
import com.example.onefit.subscription.entity.Subscription;
import com.example.onefit.user.dto.ResponseDto;
import com.example.onefit.user.dto.UserCreateDto;
import com.example.onefit.user.dto.UserResponseDto;
import com.example.onefit.user.dto.UserUpdateDto;
import com.example.onefit.user.entity.User;
import com.example.onefit.user.permission.entity.Permission;
import com.example.onefit.user.role.RoleDtoMapper;
import com.example.onefit.user.role.dto.RoleResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class UserDtoMapper extends GenericMapper<User, UserCreateDto, UserResponseDto, UserUpdateDto> {

    private final ModelMapper mapper;
    private final SubscriptionDtoMapper subscriptionDtoMapper;
    private final CourseDtoMapper courseDtoMapper;
    private final RoleDtoMapper roleDtoMapper;


    @Override
    protected ModelMapper getMapper() {
        return mapper;
    }

    @Override
    public User toEntity(UserCreateDto userCreateDto) {
        return mapper.map(userCreateDto, User.class);
    }

    public ResponseDto customResponseDto(User user) {
        ResponseDto responseDto = mapper.map(user, ResponseDto.class);

        Set<RoleResponseDto> roles = user
                .getRoles()
                .stream()
                .map(roleDtoMapper::toResponse)
                .collect(Collectors.toSet());

        if (user.getPermissions() != null) {
            Set<String> permissions = user
                    .getPermissions()
                    .stream()
                    .map(Permission::getName)
                    .collect(Collectors.toSet());

            responseDto.setPermissions(permissions);
        }
        responseDto.setRoles(roles);
        return responseDto;
    }


    @Override
    public UserResponseDto toResponse(User user) {
        UserResponseDto responseDto = mapper.map(user, UserResponseDto.class);


        Set<RoleResponseDto> roles = user
                .getRoles()
                .stream()
                .map(roleDtoMapper::toResponse)
                .collect(Collectors.toSet());

        if (user.getPermissions() != null) {
            Set<String> permissions = user
                    .getPermissions()
                    .stream()
                    .map(Permission::getName)
                    .collect(Collectors.toSet());

            if (user.getCourses() != null) {
                Set<CourseResponseDto> courses = user
                        .getCourses()
                        .stream()
                        .map(courseDtoMapper::toResponse)
                        .collect(Collectors.toSet());


                if (user.getSubscription() != null) {
                    Subscription userSubscription = user.getSubscription();
                    SubscriptionResponseDto subscription = subscriptionDtoMapper.toResponse(userSubscription);

                    responseDto.setSubscriptionResponseDto(subscription);
                    responseDto.setPermissions(permissions);
                    responseDto.setCourses(courses);
                    responseDto.setRoles(roles);
                }
            }
        }

        return responseDto;

    }


    @Override
    public void toUpdate(UserUpdateDto userUpdateDto, User user) {
        mapper.map(userUpdateDto, user);
    }
}
